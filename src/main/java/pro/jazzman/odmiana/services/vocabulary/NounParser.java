package pro.jazzman.odmiana.services.vocabulary;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.Word;
import pro.jazzman.odmiana.entities.nouns.Noun;
import pro.jazzman.odmiana.helpers.Language;
import pro.jazzman.odmiana.services.parsing.Table;

import java.io.IOException;
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
        var mianownik = rows.row(1).cells();
        var dopelniacz = rows.row(2).cells();
        var celownik = rows.row(3).cells();
        var biernik = rows.row(4).cells();
        var narzednik = rows.row(5).cells();
        var miejscownik = rows.row(6).cells();
        var wolacz = rows.row(7).cells();

        return new Noun(
            new Noun.Mianownik(mianownik.cell(1).ownText(), mianownik.cell(2).ownText()),
            new Noun.Dopelniacz(dopelniacz.cell(1).ownText(), dopelniacz.cell(2).ownText()),
            new Noun.Celownik(celownik.cell(1).ownText(), celownik.cell(2).ownText()),
            new Noun.Biernik(biernik.cell(1).ownText(), biernik.cell(2).ownText()),
            new Noun.Narzednik(narzednik.cell(1).ownText(), narzednik.cell(2).ownText()),
            new Noun.Miejscownik(miejscownik.cell(1).ownText(), miejscownik.cell(2).ownText()),
            new Noun.Wolacz(wolacz.cell(1).ownText(), wolacz.cell(2).ownText()),
            translation(document, language)
        );
    }

    private String translation(Document document, String language) {
        log.debug("Translation into '" + language + "'...");

        var pattern = "ul > li:containsOwn(" + Language.byCode(language) + ")";
        var element = document.selectFirst(pattern);

        if (element == null) {
            log.debug("Could not find the translation");
            return null;
        }

        var translations = element.select("a");

        if (translations.isEmpty()) {
            return null;
        }

        return translations.stream()
            .map(Element::ownText)
            .distinct()
            .collect(Collectors.joining(", "));
    }
}
