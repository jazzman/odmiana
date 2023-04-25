package pro.jazzman.odmiana.parsers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pro.jazzman.odmiana.entities.partsofspeech.Adjective;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.services.elements.Table;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class AdjectiveParser implements Parser {
    private final Document document;
    private final Adjective adjective = new Adjective();

    public Word parse() throws IOException {
        setBase();

        Element li = document.selectFirst("li:has(div[data-tab-name=Odmiana])");

        if (li != null) {
            Elements elements = li.select("table");

            if (elements.get(0) != null) {
                Table tableSingular = Table.from(elements.get(0));

                adjective.setSingularMaleMianownik(tableSingular.extract(2, 0, "span.forma:eq(0)"));
                adjective.setSingularMaleDopelniacz(tableSingular.extract(3, 0, "span.forma:eq(0)"));
                adjective.setSingularMaleCelownik(tableSingular.extract(4, 0, "span.forma:eq(0)"));
                adjective.setSingularMaleBiernik(tableSingular.extract(5, 0, "span.forma:eq(0)"));
                adjective.setSingularMaleNarzednik(tableSingular.extract(6, 0, "span.forma:eq(0)"));
                adjective.setSingularMaleMiejscownik(tableSingular.extract(7, 0, "span.forma:eq(0)"));
                adjective.setSingularMaleWolacz(tableSingular.extract(8, 0, "span.forma:eq(0)"));

                adjective.setSingularMaleNotAliveMianownik(tableSingular.extract(2, 2, "span.forma:eq(0)"));
                adjective.setSingularMaleNotAliveDopelniacz(tableSingular.extract(3, 2, "span.forma:eq(0)"));
                adjective.setSingularMaleNotAliveCelownik(tableSingular.extract(4, 2, "span.forma:eq(0)"));
                adjective.setSingularMaleNotAliveBiernik(tableSingular.extract(5, 2, "span.forma:eq(0)"));
                adjective.setSingularMaleNotAliveNarzednik(tableSingular.extract(6, 2, "span.forma:eq(0)"));
                adjective.setSingularMaleNotAliveMiejscownik(tableSingular.extract(7, 2, "span.forma:eq(0)"));
                adjective.setSingularMaleNotAliveWolacz(tableSingular.extract(8, 2, "span.forma:eq(0)"));

                adjective.setSingularFemaleMianownik(tableSingular.extract(2, 4, "span.forma:eq(0)"));
                adjective.setSingularFemaleDopelniacz(tableSingular.extract(3, 4, "span.forma:eq(0)"));
                adjective.setSingularFemaleCelownik(tableSingular.extract(4, 4, "span.forma:eq(0)"));
                adjective.setSingularFemaleBiernik(tableSingular.extract(5, 4, "span.forma:eq(0)"));
                adjective.setSingularFemaleNarzednik(tableSingular.extract(6, 4, "span.forma:eq(0)"));
                adjective.setSingularFemaleMiejscownik(tableSingular.extract(7, 4, "span.forma:eq(0)"));
                adjective.setSingularFemaleWolacz(tableSingular.extract(8, 4, "span.forma:eq(0)"));

                adjective.setSingularNeutralMianownik(tableSingular.extract(2, 3, "span.forma:eq(0)"));
                adjective.setSingularNeutralDopelniacz(tableSingular.extract(3, 3, "span.forma:eq(0)"));
                adjective.setSingularNeutralCelownik(tableSingular.extract(4, 3, "span.forma:eq(0)"));
                adjective.setSingularNeutralBiernik(tableSingular.extract(5, 3, "span.forma:eq(0)"));
                adjective.setSingularNeutralNarzednik(tableSingular.extract(6, 3, "span.forma:eq(0)"));
                adjective.setSingularNeutralMiejscownik(tableSingular.extract(7, 3, "span.forma:eq(0)"));
                adjective.setSingularNeutralWolacz(tableSingular.extract(8, 3, "span.forma:eq(0)"));
            } else {
                log.warn("Unable to find a table on the page to get a singular forms of the adjective");
            }

            if (elements.get(1) != null) {
                Table tablePlural = Table.from(elements.get(1));

                adjective.setPluralMaleMianownik(tablePlural.extract(2, 0, "span.forma:eq(0)"));
                adjective.setPluralMaleDopelniacz(tablePlural.extract(3, 0, "span.forma:eq(0)"));
                adjective.setPluralMaleCelownik(tablePlural.extract(4, 0, "span.forma:eq(0)"));
                adjective.setPluralMaleBiernik(tablePlural.extract(5, 0, "span.forma:eq(0)"));
                adjective.setPluralMaleNarzednik(tablePlural.extract(6, 0, "span.forma:eq(0)"));
                adjective.setPluralMaleMiejscownik(tablePlural.extract(7, 0, "span.forma:eq(0)"));
                adjective.setPluralMaleWolacz(tablePlural.extract(8, 0, "span.forma:eq(0)"));

                adjective.setPluralNonMaleMianownik(tablePlural.extract(2, 3, "span.forma:eq(0)"));
                adjective.setPluralNonMaleDopelniacz(tablePlural.extract(3, 3, "span.forma:eq(0)"));
                adjective.setPluralNonMaleCelownik(tablePlural.extract(4, 3, "span.forma:eq(0)"));
                adjective.setPluralNonMaleBiernik(tablePlural.extract(5, 3, "span.forma:eq(0)"));
                adjective.setPluralNonMaleNarzednik(tablePlural.extract(6, 3, "span.forma:eq(0)"));
                adjective.setPluralNonMaleMiejscownik(tablePlural.extract(7, 3, "span.forma:eq(0)"));
                adjective.setPluralNonMaleWolacz(tablePlural.extract(8, 3, "span.forma:eq(0)"));
            } else {
                log.warn("Unable to find a table on the page to get a plural forms of the adjective");
            }
        }

        return adjective;
    }

    private void setBase() {
        Element h1 = document.selectFirst("h1");

        if (h1 != null) {
            adjective.setBase(h1.text());
        } else {
            log.warn("Unable to find a header on the page to get a base form of the word");
        }
    }
}
