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

                adjective.put("pojedyncza.męskoosobowy.mianownik",      tableSingular.extract(2, 0, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskoosobowy.dopelniacz",     tableSingular.extract(3, 0, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskoosobowy.celownik",       tableSingular.extract(4, 0, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskoosobowy.biernik",        tableSingular.extract(5, 0, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskoosobowy.narzędnik",      tableSingular.extract(6, 0, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskoosobowy.miejscownik",    tableSingular.extract(7, 0, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskoosobowy.wolacz",         tableSingular.extract(8, 0, "span.forma:eq(0)"));

                adjective.put("pojedyncza.męskorzeczowy.mianownik",     tableSingular.extract(2, 2, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskorzeczowy.dopelniacz",    tableSingular.extract(3, 2, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskorzeczowy.celownik",      tableSingular.extract(4, 2, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskorzeczowy.biernik",       tableSingular.extract(5, 2, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskorzeczowy.narzędnik",     tableSingular.extract(6, 2, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskorzeczowy.miejscownik",   tableSingular.extract(7, 2, "span.forma:eq(0)"));
                adjective.put("pojedyncza.męskorzeczowy.wolacz",        tableSingular.extract(8, 2, "span.forma:eq(0)"));

                adjective.put("pojedyncza.żeński.mianownik",            tableSingular.extract(2, 4, "span.forma:eq(0)"));
                adjective.put("pojedyncza.żeński.dopelniacz",           tableSingular.extract(3, 4, "span.forma:eq(0)"));
                adjective.put("pojedyncza.żeński.celownik",             tableSingular.extract(4, 4, "span.forma:eq(0)"));
                adjective.put("pojedyncza.żeński.biernik",              tableSingular.extract(5, 4, "span.forma:eq(0)"));
                adjective.put("pojedyncza.żeński.narzędnik",            tableSingular.extract(6, 4, "span.forma:eq(0)"));
                adjective.put("pojedyncza.żeński.miejscownik",          tableSingular.extract(7, 4, "span.forma:eq(0)"));
                adjective.put("pojedyncza.żeński.wolacz",               tableSingular.extract(8, 4, "span.forma:eq(0)"));

                adjective.put("pojedyncza.nijaki.mianownik",            tableSingular.extract(2, 3, "span.forma:eq(0)"));
                adjective.put("pojedyncza.nijaki.dopelniacz",           tableSingular.extract(3, 3, "span.forma:eq(0)"));
                adjective.put("pojedyncza.nijaki.celownik",             tableSingular.extract(4, 3, "span.forma:eq(0)"));
                adjective.put("pojedyncza.nijaki.biernik",              tableSingular.extract(5, 3, "span.forma:eq(0)"));
                adjective.put("pojedyncza.nijaki.narzędnik",            tableSingular.extract(6, 3, "span.forma:eq(0)"));
                adjective.put("pojedyncza.nijaki.miejscownik",          tableSingular.extract(7, 3, "span.forma:eq(0)"));
                adjective.put("pojedyncza.nijaki.wolacz",               tableSingular.extract(8, 3, "span.forma:eq(0)"));
            } else {
                log.warn("Unable to find a table on the page to get a singular forms of the adjective");
            }

            if (elements.get(1) != null) {
                Table tablePlural = Table.from(elements.get(1));

                adjective.put("mnoga.męskoosobowy.mianownik",      tablePlural.extract(2, 0, "span.forma:eq(0)"));
                adjective.put("mnoga.męskoosobowy.dopelniacz",     tablePlural.extract(3, 0, "span.forma:eq(0)"));
                adjective.put("mnoga.męskoosobowy.celownik",       tablePlural.extract(4, 0, "span.forma:eq(0)"));
                adjective.put("mnoga.męskoosobowy.biernik",        tablePlural.extract(5, 0, "span.forma:eq(0)"));
                adjective.put("mnoga.męskoosobowy.narzędnik",      tablePlural.extract(6, 0, "span.forma:eq(0)"));
                adjective.put("mnoga.męskoosobowy.miejscownik",    tablePlural.extract(7, 0, "span.forma:eq(0)"));
                adjective.put("mnoga.męskoosobowy.wolacz",         tablePlural.extract(8, 0, "span.forma:eq(0)"));

                adjective.put("mnoga.niemęskoosobowy.mianownik",      tablePlural.extract(2, 3, "span.forma:eq(0)"));
                adjective.put("mnoga.niemęskoosobowy.dopelniacz",     tablePlural.extract(3, 3, "span.forma:eq(0)"));
                adjective.put("mnoga.niemęskoosobowy.celownik",       tablePlural.extract(4, 3, "span.forma:eq(0)"));
                adjective.put("mnoga.niemęskoosobowy.biernik",        tablePlural.extract(5, 3, "span.forma:eq(0)"));
                adjective.put("mnoga.niemęskoosobowy.narzędnik",      tablePlural.extract(6, 3, "span.forma:eq(0)"));
                adjective.put("mnoga.niemęskoosobowy.miejscownik",    tablePlural.extract(7, 3, "span.forma:eq(0)"));
                adjective.put("mnoga.niemęskoosobowy.wolacz",         tablePlural.extract(8, 3, "span.forma:eq(0)"));
            } else {
                log.warn("Unable to find a table on the page to get a plural forms of the adjective");
            }

            if (elements.get(2) != null) {
                Table tableSingular = Table.from(elements.get(2));

                adjective.put("wyższy.pojedyncza.męskoosobowy.mianownik",      tableSingular.extract(2, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskoosobowy.dopelniacz",     tableSingular.extract(3, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskoosobowy.celownik",       tableSingular.extract(4, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskoosobowy.biernik",        tableSingular.extract(5, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskoosobowy.narzędnik",      tableSingular.extract(6, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskoosobowy.miejscownik",    tableSingular.extract(7, 0, "span.forma:eq(0)"));

                adjective.put("wyższy.pojedyncza.męskorzeczowy.mianownik",     tableSingular.extract(2, 2, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskorzeczowy.dopelniacz",    tableSingular.extract(3, 2, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskorzeczowy.celownik",      tableSingular.extract(4, 2, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskorzeczowy.biernik",       tableSingular.extract(5, 2, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskorzeczowy.narzędnik",     tableSingular.extract(6, 2, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.męskorzeczowy.miejscownik",   tableSingular.extract(7, 2, "span.forma:eq(0)"));

                adjective.put("wyższy.pojedyncza.żeński.mianownik",            tableSingular.extract(2, 4, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.żeński.dopelniacz",           tableSingular.extract(3, 4, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.żeński.celownik",             tableSingular.extract(4, 4, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.żeński.biernik",              tableSingular.extract(5, 4, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.żeński.narzędnik",            tableSingular.extract(6, 4, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.żeński.miejscownik",          tableSingular.extract(7, 4, "span.forma:eq(0)"));

                adjective.put("wyższy.pojedyncza.nijaki.mianownik",            tableSingular.extract(2, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.nijaki.dopelniacz",           tableSingular.extract(3, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.nijaki.celownik",             tableSingular.extract(4, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.nijaki.biernik",              tableSingular.extract(5, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.nijaki.narzędnik",            tableSingular.extract(6, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.pojedyncza.nijaki.miejscownik",          tableSingular.extract(7, 3, "span.forma:eq(0)"));
            } else {
                log.warn("Unable to find a table on the page to get a comparative singular forms of the adjective");
            }

            if (elements.get(3) != null) {
                Table tablePlural = Table.from(elements.get(3));

                adjective.put("wyższy.mnoga.męskoosobowy.mianownik",      tablePlural.extract(2, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.męskoosobowy.dopelniacz",     tablePlural.extract(3, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.męskoosobowy.celownik",       tablePlural.extract(4, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.męskoosobowy.biernik",        tablePlural.extract(5, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.męskoosobowy.narzędnik",      tablePlural.extract(6, 0, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.męskoosobowy.miejscownik",    tablePlural.extract(7, 0, "span.forma:eq(0)"));

                adjective.put("wyższy.mnoga.niemęskoosobowy.mianownik",      tablePlural.extract(2, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.niemęskoosobowy.dopelniacz",     tablePlural.extract(3, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.niemęskoosobowy.celownik",       tablePlural.extract(4, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.niemęskoosobowy.biernik",        tablePlural.extract(5, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.niemęskoosobowy.narzędnik",      tablePlural.extract(6, 3, "span.forma:eq(0)"));
                adjective.put("wyższy.mnoga.niemęskoosobowy.miejscownik",    tablePlural.extract(7, 3, "span.forma:eq(0)"));
            } else {
                log.warn("Unable to find a table on the page to get a plural forms of the adjective");
            }
        }

        return adjective;
    }

    private void setBase() {
        Element h1 = document.selectFirst("h1");

        if (h1 != null) {
            adjective.setBase(h1.text().replaceAll(" I", "").trim());
        } else {
            log.warn("Unable to find a header on the page to get a base form of the word");
        }
    }
}
