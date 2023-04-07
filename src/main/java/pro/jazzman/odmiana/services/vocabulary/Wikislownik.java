package pro.jazzman.odmiana.services.vocabulary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.jazzman.odmiana.entities.Word;
import pro.jazzman.odmiana.services.parsers.NounParser;
import pro.jazzman.odmiana.services.parsers.Parser;
import pro.jazzman.odmiana.services.parsers.VerbParser;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class Wikislownik {
    private final String url;
    public Wikislownik(@Value("${wikislownik.url}") final String url) {
        this.url = url;
    }

    public Optional<Word> get(String text, String language) {
        try {
            var page = page(text.toLowerCase());

            var document = Jsoup.parse(
                html(page)
            );

            var parser = parser(page);

            return Optional.of(parser.parse(document, language));
        } catch (Exception e) {
            log.error("Unable to retrieve the result for the word '" + text + "'");
        }

        return Optional.empty();
    }

    private JsonNode page(String word) throws Exception {

        var response = get(url + word);

        if (response.getBody() == null) {
            throw new Exception("Response is empty");
        }

        return new ObjectMapper().readTree(response.getBody());
    }

    private String html(JsonNode page) throws Exception {
        var content = page.path("parse").path("text").path("*");

        if (content.isMissingNode()) {
            throw new IOException("Unable to find HTML content: 'parse.text.*'");
        }

        return content.asText();
    }

    private Parser parser(JsonNode page) throws IOException {
        var categories = page.path("parse").path("categories");

        if (!categories.isArray()) {
            throw new IOException("Unable to resolve the part of speech: 'parse.categories'");
        }

        for (var entry: categories) {
            var category = entry.path("*");

            if (category.isMissingNode()) {
                continue;
            }

            for (var partOfSpeech : PartOfSpeech.values()) {
                if (category.asText().contains(partOfSpeech.getInPolish())) {
                    return switch (partOfSpeech) {
                        case VERB -> new VerbParser();
                        case NOUN -> new NounParser();
                    };
                }
            }
        }

        throw new IOException("Unable to create a parser");
    }

    private ResponseEntity<String> get(String url) {
        return new RestTemplate().getForEntity(url, String.class);
    }
}
