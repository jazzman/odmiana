package pro.jazzman.odmiana.services.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.Word;
import pro.jazzman.odmiana.entities.nouns.Noun;
import pro.jazzman.odmiana.helpers.Language;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class NounParser implements Parser {
    private static final String TABLE_SELECTOR = "table.wikitable.odmiana";
    @Override
    public Word parse(Document document, String language) throws Exception {
        Element element = document.selectFirst(TABLE_SELECTOR);

        if (element == null) {
            throw new IOException("Unable to get table for the noun by the following selector: '" + TABLE_SELECTOR + "'");
        }

        var table = Table.from(element);

        var rows = table.rows();

        var header = rows.row(0).headerCells();

        var mianownik = rows.row(1).cells();
        var dopelniacz = rows.row(2).cells();
        var celownik = rows.row(3).cells();
        var biernik = rows.row(4).cells();
        var narzednik = rows.row(5).cells();
        var miejscownik = rows.row(6).cells();
        var wolacz = rows.row(7).cells();

        if ("liczba mnoga".equals(header.cell(1).text()) && header.cell(2) == null) {
            return new Noun(
                new Noun.Mianownik(null, mianownik.cell(1) != null ? mianownik.cell(1).text() : null),
                new Noun.Dopelniacz(null, dopelniacz.cell(1) != null ? dopelniacz.cell(1).text() : null),
                new Noun.Celownik(null, celownik.cell(1) != null ? celownik.cell(1).text() : null),
                new Noun.Biernik(null, biernik.cell(1) != null ? biernik.cell(1).text() : null),
                new Noun.Narzednik(null, narzednik.cell(1) != null ? narzednik.cell(1).text() : null),
                new Noun.Miejscownik(null, miejscownik.cell(1) != null ? miejscownik.cell(1).text() : null),
                new Noun.Wolacz(null, wolacz.cell(1) != null ? wolacz.cell(1).text() : null),
                translation(document, language)
            );
        }

        if ("liczba pojedyncza".equals(header.cell(1).text()) && header.cell(2) == null) {
            return new Noun(
                new Noun.Mianownik(mianownik.cell(1) != null ? mianownik.cell(1).text() : null, null),
                new Noun.Dopelniacz(dopelniacz.cell(1) != null ? dopelniacz.cell(1).text() : null, null),
                new Noun.Celownik(celownik.cell(1) != null ? celownik.cell(1).text() : null, null),
                new Noun.Biernik(biernik.cell(1) != null ? biernik.cell(1).text() : null, null),
                new Noun.Narzednik(narzednik.cell(1) != null ? narzednik.cell(1).text() : null, null),
                new Noun.Miejscownik(miejscownik.cell(1) != null ? miejscownik.cell(1).text() : null, null),
                new Noun.Wolacz(wolacz.cell(1) != null ? wolacz.cell(1).text() : null, null),
                translation(document, language)
            );
        }

        if ("liczba pojedyncza".equals(header.cell(1).text()) && "liczba mnoga".equals(header.cell(2).text())) {
            return new Noun(
                new Noun.Mianownik(
                    mianownik.cell(1) != null ? mianownik.cell(1).text() : null,
                    mianownik.cell(2) != null ? mianownik.cell(2).text() : null
                ),
                new Noun.Dopelniacz(
                    dopelniacz.cell(1) != null ? dopelniacz.cell(1).text() : null,
                    dopelniacz.cell(2) != null ? dopelniacz.cell(2).text() : null
                ),
                new Noun.Celownik(
                    celownik.cell(1) != null ? celownik.cell(1).text() : null,
                    celownik.cell(2) != null ? celownik.cell(2).text() : null
                ),
                new Noun.Biernik(
                    biernik.cell(1) != null ? biernik.cell(1).text() : null,
                    biernik.cell(2) != null ? biernik.cell(2).text() : null
                ),
                new Noun.Narzednik(
                    narzednik.cell(1) != null ? narzednik.cell(1).text() : null,
                    narzednik.cell(2) != null ? narzednik.cell(2).text() : null
                ),
                new Noun.Miejscownik(
                    miejscownik.cell(1) != null ? miejscownik.cell(1).text() : null,
                    miejscownik.cell(2) != null ? miejscownik.cell(2).text() : null
                ),
                new Noun.Wolacz(
                    wolacz.cell(1) != null ? wolacz.cell(1).text() : null,
                    wolacz.cell(2) != null ? wolacz.cell(2).text() : null
                ),
                translation(document, language)
            );
        }

        throw new RuntimeException("Unable to parse the page");
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

        var delimeter = text.split(";").length > 0 ? ";" : ",";

        return Arrays.stream(text.split(delimeter))
            .map(String::trim)
            .map(w -> Arrays.stream(w.split(" "))
                .filter(e -> e.length() > 1)
                .collect(Collectors.joining(" "))
            )
            .distinct()
            .collect(Collectors.joining(", "));
    }
}
