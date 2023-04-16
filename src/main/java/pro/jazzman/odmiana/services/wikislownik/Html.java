package pro.jazzman.odmiana.services.wikislownik;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
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

        word.setTranslation(translation(lang));

        return word;
    }

    public String translation(String language) {
        var pattern = "ul > li:containsOwn(" + Language.byCode(language) + ")";
        var element = document.selectFirst(pattern);

        if (element == null) {
            log.debug("Could not find the translation");
            return null;
        }

        return Arrays.stream(
                element
                    .text()
                    .substring(element.text().indexOf(":") + 2)
                    .split(";")
            )
            .map(e -> e.split(","))
            .flatMap(Stream::of)
            .map(e -> e.replaceAll("\\s*\\(\\S+\\)\\s*", ""))
            .map(e -> e.replaceAll("\\s[żmwn]$", ""))
            .map(String::trim)
            .distinct()
            .collect(Collectors.joining(", "));
    }
}
