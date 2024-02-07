package pro.jazzman.odmiana.parsers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.partsofspeech.NounType;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class NounParser implements Parser {
    /**
     * Parses the document and assign all the forms to the word
     * @return a noun with all possible forms
     * @throws IOException if there is an issue with content retrieval
     */
    public Word parse(Document document) throws IOException {
        var noun = new Noun();

        base(noun, document);

        Element li = document.selectFirst("li:has(div[data-tab-name=Odmiana])");

        if (li != null) {
            assignType(noun, li);
            assignCases(noun, li);
        }

        return noun;
    }

    /**
     * Assigns a base form of the noun
     * @param noun a noun
     * @param document document to be parsed
     */
    private void base(Noun noun, Document document) {
        Element h1 = document.selectFirst("h1");

        if (h1 != null) {
            noun.setBase(h1.text().replaceAll(" I", "").trim());
        } else {
            log.warn("Unable to find a header on the page to get a base form of the word");
        }
    }

    /**
     * Assign a type, i.e. masculine, feminine, neuter to the noun
     * @param noun a noun
     * @param li the menu list element that contains data to parse
     */
    private void assignType(Noun noun, Element li) {
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

    /**
     * Assign noun cases to the noun
     * @param noun a noun
     * @param li the menu list element that contains data to parse
     */
    private void assignCases(Noun noun, Element li) {
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

    @Nullable
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
