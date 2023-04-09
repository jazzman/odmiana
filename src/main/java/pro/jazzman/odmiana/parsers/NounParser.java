package pro.jazzman.odmiana.parsers;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import pro.jazzman.odmiana.services.elements.Cells;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;

@Slf4j
public class NounParser implements Parser {
    private static final String TABLE_SELECTOR = "table.wikitable.odmiana";
    @Override
    public Word parse(Document document) throws IOException {
        Element element = document.selectFirst(TABLE_SELECTOR);

        if (element == null) {
            throw new IOException("Unable to get table for the noun by the following selector: '" + TABLE_SELECTOR + "'");
        }

        var table = Table.from(element);
        var rows = table.rows();
        Cells header = rows.row(0).headerCells();

        var mianownik = rows.row(1).cells();
        var dopelniacz = rows.row(2).cells();
        var celownik = rows.row(3).cells();
        var biernik = rows.row(4).cells();
        var narzednik = rows.row(5).cells();
        var miejscownik = rows.row(6).cells();
        var wolacz = rows.row(7).cells();

        Noun noun = new Noun();

        if ("liczba pojedyncza".equals(header.textInCell(1))) {
            noun.setMianownikSingular(mianownik.textInCell(1));
            noun.setDopelniaczSingular(dopelniacz.textInCell(1));
            noun.setCelownikSingular(celownik.textInCell(1));
            noun.setBiernikSingular(biernik.textInCell(1));
            noun.setNarzednikSingular(narzednik.textInCell(1));
            noun.setMiejscownikSingular(miejscownik.textInCell(1));
            noun.setWolaczSingular(wolacz.textInCell(1));

            if ("liczba mnoga".equals(header.textInCell(2))) {
                noun.setMianownikPlural(mianownik.textInCell(2));
                noun.setDopelniaczPlural(dopelniacz.textInCell(2));
                noun.setCelownikPlural(celownik.textInCell(2));
                noun.setBiernikPlural(biernik.textInCell(2));
                noun.setNarzednikPlural(narzednik.textInCell(2));
                noun.setMiejscownikPlural(miejscownik.textInCell(2));
                noun.setWolaczPlural(wolacz.textInCell(2));
            }
        }

        if ("liczba mnoga".equals(header.textInCell(1))) {
            noun.setMianownikPlural(mianownik.textInCell(1));
            noun.setDopelniaczPlural(dopelniacz.textInCell(1));
            noun.setCelownikPlural(celownik.textInCell(1));
            noun.setBiernikPlural(biernik.textInCell(1));
            noun.setNarzednikPlural(narzednik.textInCell(1));
            noun.setMiejscownikPlural(miejscownik.textInCell(1));
            noun.setWolaczPlural(wolacz.textInCell(1));
        }

        return noun;
    }
}
