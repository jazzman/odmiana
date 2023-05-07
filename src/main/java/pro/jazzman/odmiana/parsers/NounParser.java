package pro.jazzman.odmiana.parsers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pro.jazzman.odmiana.entities.partsofspeech.NounType;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import pro.jazzman.odmiana.services.elements.Cells;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class NounParser implements Parser {
    private final Document document;
    private final Noun noun = new Noun();

    public Word parse() throws IOException {
        setBase();

        Element li = document.selectFirst("li:has(div[data-tab-name=Odmiana])");

        if (li != null) {
            setType(li);
            setCases(li);
        }

        return noun;
    }

    private void setBase() {
        Element h1 = document.selectFirst("h1");

        if (h1 != null) {
            noun.setBase(h1.text());
        } else {
            log.warn("Unable to find a header on the page to get a base form of the word");
        }
    }

    private void setType(Element li) {
        Element em = li.selectFirst("p:contains(rodzaj gramatyczny:) span em");

        if (em != null) {
            Optional<NounType> type = Arrays.stream(NounType.values())
                .filter(t -> t.getAbbr().equals(em.text()))
                .findFirst();

            type.ifPresent(noun::setType);

            return;
        }

        log.warn("Unable to find noun type on the page");
    }

    private void setCases(Element li) {
        Table table = table(li);

        if (table == null) {
            log.warn("Cannot find a table with word cases");

            return;
        }

        noun.put("pojedyncza.mianownik",    table.extract(1, 0, "span.forma:eq(0)"));
        noun.put("pojedyncza.dopelniacz",   table.extract(2, 0, "span.forma:eq(0)"));
        noun.put("pojedyncza.celownik",     table.extract(3, 0, "span.forma:eq(0)"));
        noun.put("pojedyncza.biernik",      table.extract(4, 0, "span.forma:eq(0)"));
        noun.put("pojedyncza.narzędnik",    table.extract(5, 0, "span.forma:eq(0)"));
        noun.put("pojedyncza.miejscownik",  table.extract(6, 0, "span.forma:eq(0)"));
        noun.put("pojedyncza.wolacz",       table.extract(7, 0, "span.forma:eq(0)"));

        noun.put("mnoga.mianownik",    table.extract(1, 1, "span.forma:eq(0)"));
        noun.put("mnoga.dopelniacz",   table.extract(2, 1, "span.forma:eq(0)"));
        noun.put("mnoga.celownik",     table.extract(3, 1, "span.forma:eq(0)"));
        noun.put("mnoga.biernik",      table.extract(4, 1, "span.forma:eq(0)"));
        noun.put("mnoga.narzędnik",    table.extract(5, 1, "span.forma:eq(0)"));
        noun.put("mnoga.miejscownik",  table.extract(6, 1, "span.forma:eq(0)"));
        noun.put("mnoga.wolacz",       table.extract(7, 1, "span.forma:eq(0)"));
    }

    private Table table(Element li) {
        Element element = li.selectFirst("table");

        if (element == null) {
            return null;
        }

        Elements rows = element.select("tr");

        for (Element tr: rows) {
            if (tr.text().isEmpty()) {
                tr.remove();
            }
        }

        return Table.from(element);
    }
}
