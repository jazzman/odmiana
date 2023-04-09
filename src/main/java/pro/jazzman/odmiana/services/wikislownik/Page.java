package pro.jazzman.odmiana.services.wikislownik;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
import pro.jazzman.odmiana.parsers.NounParser;
import pro.jazzman.odmiana.parsers.Parser;
import pro.jazzman.odmiana.parsers.VerbParser;
import pro.jazzman.odmiana.services.vocabulary.PartOfSpeech;

import java.io.IOException;

@AllArgsConstructor
public class Page {
    private final String url;
    private final String word;

    public Html html() throws Exception {
        var response = get(url + word);

        if (response.getBody() == null) {
            throw new ApplicationRuntimeException("Response is empty");
        }

        JsonNode body = new ObjectMapper().readTree(response.getBody());

        var content = body.path("parse").path("text").path("*");

        if (content.isMissingNode()) {
            throw new IOException("Unable to find HTML content: 'parse.text.*'");
        }
        return new Html(content.asText(), parser(body));
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
