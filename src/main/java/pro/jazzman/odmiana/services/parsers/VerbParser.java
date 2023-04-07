package pro.jazzman.odmiana.services.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.Word;
import pro.jazzman.odmiana.entities.verbs.*;
import pro.jazzman.odmiana.helpers.Language;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class VerbParser implements Parser {
    private static final String TABLE_SELECTOR = "table.wikitable";

    public Word parse(Document document, String language) throws Exception {
        Element element = document.selectFirst(TABLE_SELECTOR);

        if (element == null) {
            throw new IOException("Unable to get table for the verb by the following selector: '" + TABLE_SELECTOR + "'");
        }

        var table = Table.from(element);

        var rows = table.rows();
        var present = rows.row(3).cells();
        var pastM = rows.row(4).cells();
        var pastF = rows.row(5).cells();
        var pastN = rows.row(6).cells();
        var imperative = rows.row(7).cells();

        return new Verb(
            infinitive(table),
            new Singular(
                new Present(present.cell(0).text(), present.cell(1).text(), present.cell(2).text()),
                new Past(
                    new Masculine(pastM.cell(0).text(), pastM.cell(1).text(), pastM.cell(2).text()),
                    new Feminine(pastF.cell(0).text(), pastF.cell(1).text(), pastF.cell(2).text()),
                    new Neutral(pastN.cell(0).text(), pastN.cell(1).text(), pastN.cell(2).text())
                ),
                new Imperative(imperative.cell(0).text(), imperative.cell(1).text(), imperative.cell(2).text())
            ),
            new Plural(
                new Present(present.cell(3).text(), present.cell(4).text(), present.cell(5).text()),
                new Past(
                    new Masculine(pastM.cell(3).text(), pastM.cell(4).text(), pastM.cell(5).text()),
                    new FeminineAndNeutral(pastF.cell(3).text(), pastF.cell(4).text(), pastF.cell(5).text())
                ),
                new Imperative(imperative.cell(3).text(), imperative.cell(4).text(), imperative.cell(5).text())
            ),
            translation(document, language)
        );
    }

    private String infinitive(Table table) throws Exception {
        var infinitive = table.rows().row(2).cells().cell(0).selectFirst("b");

        if (infinitive == null) {
            throw new Exception("Unable to find an infinitive");
        }

        return infinitive.text();
    }

    private String translation(Document document, String language) {
        log.debug("Translation into '" + language + "'...");

        var pattern = "ul > li:containsOwn(" + Language.byCode(language) + ")";
        var element = document.selectFirst(pattern);

        if (element == null) {
            log.debug("Could not find the translation");
            return null;
        }

        var text = element
            .text()
            .replaceAll("\\(\\d+\\.\\d+\\) ", ""); // remove enumeration

        text = text.substring(text.indexOf(":") + 2); // remove language

        return Arrays.stream(text.split(";"))
            .map(String::trim)
            .distinct()
            .collect(Collectors.joining(", "));
    }
}
