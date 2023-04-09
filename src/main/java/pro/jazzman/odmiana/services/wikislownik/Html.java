package pro.jazzman.odmiana.services.wikislownik;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pro.jazzman.odmiana.ApplicationRuntimeException;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.helpers.Language;
import pro.jazzman.odmiana.parsers.Parser;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Html {
    private final Document document;
    private final Parser parser;

    public Html(String text, Parser parser) {
        this.document = Jsoup.parse(text);
        this.parser = parser;
    }

    public Word parse(String lang) throws Exception {
        Word word = parser.parse(document);

        if (word == null) {
            throw new ApplicationRuntimeException("Unable to parse the html");
        }

        word.addTranslation(translation(lang));

        return word;
    }

    public String translation(String language) {
        var pattern = "ul > li:containsOwn(" + Language.byCode(language) + ")";
        var element = document.selectFirst(pattern);

        if (element == null) {
            log.debug("Could not find the translation");
            return null;
        }

        String text = element.text();

        return Arrays.stream(
                text.substring(text.indexOf(":") + 2) // remove language
                    .replaceAll("\\(\\d+\\.\\d+\\) ", "") // remove enumeration
                    .split(";")
            )
            .map(e -> e.split(","))
            .flatMap(Stream::of)
            .map(String::trim)
            .map(e -> e.replaceAll("\\s+[żmwn]$", "")) // remove kind
            .distinct()
            .collect(Collectors.joining(", "));
    }


}
