package pro.jazzman.odmiana.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;

@Slf4j
public class NounParser implements Parser {
    private static final String TABLE_SELECTOR = "table.wikitable.odmiana";
    @Override
    public Word parse(Document document) throws Exception {
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

        Noun noun = new Noun();

        if (header.cell(1) != null && "liczba pojedyncza".equals(header.cell(1).text())) {
            noun.setMianownikSingular(mianownik.cell(1) != null ? mianownik.cell(1).text() : "");
            noun.setDopelniaczSingular(dopelniacz.cell(1) != null ? dopelniacz.cell(1).text() : "");
            noun.setCelownikSingular(celownik.cell(1) != null ? celownik.cell(1).text() : "");
            noun.setBiernikSingular(biernik.cell(1) != null ? biernik.cell(1).text() : "");
            noun.setNarzednikSingular(narzednik.cell(1) != null ? narzednik.cell(1).text() : "");
            noun.setMiejscownikSingular(miejscownik.cell(1) != null ? miejscownik.cell(1).text() : "");
            noun.setWolaczSingular(wolacz.cell(1) != null ? wolacz.cell(1).text() : "");

            if (header.cell(2) != null && "liczba mnoga".equals(header.cell(2).text())) {
                noun.setMianownikPlural(mianownik.cell(2) != null ? mianownik.cell(2).text() : "");
                noun.setDopelniaczPlural(dopelniacz.cell(2) != null ? dopelniacz.cell(2).text() : "");
                noun.setCelownikPlural(celownik.cell(2) != null ? celownik.cell(2).text() : "");
                noun.setBiernikPlural(biernik.cell(2) != null ? biernik.cell(2).text() : "");
                noun.setNarzednikPlural(narzednik.cell(2) != null ? narzednik.cell(2).text() : "");
                noun.setMiejscownikPlural(miejscownik.cell(2) != null ? miejscownik.cell(2).text() : "");
                noun.setWolaczPlural(wolacz.cell(2) != null ? wolacz.cell(2).text() : "");
            }
        }

        if (header.cell(1) != null && "liczba mnoga".equals(header.cell(1).text())) {
            noun.setMianownikPlural(mianownik.cell(1) != null ? mianownik.cell(1).text() : "");
            noun.setDopelniaczPlural(dopelniacz.cell(1) != null ? dopelniacz.cell(1).text() : "");
            noun.setCelownikPlural(celownik.cell(1) != null ? celownik.cell(1).text() : "");
            noun.setBiernikPlural(biernik.cell(1) != null ? biernik.cell(1).text() : "");
            noun.setNarzednikPlural(narzednik.cell(1) != null ? narzednik.cell(1).text() : "");
            noun.setMiejscownikPlural(miejscownik.cell(1) != null ? miejscownik.cell(1).text() : "");
            noun.setWolaczPlural(wolacz.cell(1) != null ? wolacz.cell(1).text() : "");
        }

        return noun;
    }
}
