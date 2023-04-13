package pro.jazzman.odmiana.parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.partsofspeech.Adjective;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.services.elements.Table;

import java.io.IOException;

public class AdjectiveParser implements Parser {
    private static final String TABLE_SELECTOR = "table.wikitable.odmiana.adj";

    @Override
    public Word parse(Document document) throws IOException {
        Element element = document.selectFirst(TABLE_SELECTOR);

        if (element == null) {
            throw new IOException("Unable to get table for the adjective by the following selector: '" + TABLE_SELECTOR + "'");
        }

        var table = Table.from(element);
        var rows = table.rows();

        var mianownik = rows.row(2).cells();
        var dopelniacz = rows.row(3).cells();
        var celownik = rows.row(4).cells();
        var biernik = rows.row(5).cells();
        var narzednik = rows.row(6).cells();
        var miejscownik = rows.row(7).cells();
        var wolacz = rows.row(8).cells();

        Adjective adjective = new Adjective();

        adjective.setSingularMaleMianownik(mianownik.textInCell(1));
        adjective.setSingularMaleDopelniacz(dopelniacz.textInCell(1));
        adjective.setSingularMaleCelownik(celownik.textInCell(1));
        adjective.setSingularMaleAliveBiernik(biernik.textInCell(1));
        adjective.setSingularMaleNotAliveBiernik(biernik.textInCell(2));
        adjective.setSingularMaleNarzednik(narzednik.textInCell(1));
        adjective.setSingularMaleMiejscownik(miejscownik.textInCell(1));
        adjective.setSingularMaleWolac(wolacz.textInCell(1));

        adjective.setSingularFemaleMianownik(mianownik.textInCell(2));
        adjective.setSingularFemaleDopelniacz(dopelniacz.textInCell(2));
        adjective.setSingularFemaleCelownik(celownik.textInCell(2));
        adjective.setSingularFemaleBiernik(biernik.textInCell(3));
        adjective.setSingularFemaleNarzednik(narzednik.textInCell(2));
        adjective.setSingularFemaleMiejscownik(miejscownik.textInCell(2));
        adjective.setSingularFemaleWolac(wolacz.textInCell(2));

        adjective.setSingularNeutralMianownik(mianownik.textInCell(3));
        adjective.setSingularNeutralDopelniacz(dopelniacz.textInCell(3));
        adjective.setSingularNeutralCelownik(celownik.textInCell(3));
        adjective.setSingularNeutralBiernik(biernik.textInCell(4));
        adjective.setSingularNeutralNarzednik(narzednik.textInCell(3));
        adjective.setSingularNeutralMiejscownik(miejscownik.textInCell(3));
        adjective.setSingularNeutralWolac(wolacz.textInCell(3));

        adjective.setPluralMaleMianownik(mianownik.textInCell(4));
        adjective.setPluralNonMaleMianownik(mianownik.textInCell(5));
        adjective.setPluralDopelniacz(dopelniacz.textInCell(4));
        adjective.setPluralCelownik(celownik.textInCell(4));
        adjective.setPluralMaleBiernik(biernik.textInCell(5));
        adjective.setPluralNonMaleBiernik(biernik.textInCell(6));
        adjective.setPluralNarzednik(narzednik.textInCell(4));
        adjective.setPluralMiejscownik(miejscownik.textInCell(4));
        adjective.setPluralMaleWolac(wolacz.textInCell(4));
        adjective.setPluralNonMaleWolac(wolacz.textInCell(5));

        return adjective;
    }
}
