package pro.jazzman.odmiana.parsers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class VerbParser implements Parser {
    /**
     * Parses the document and assign all the forms to the word
     * @return a verb with all possible forms
     * @throws IOException if there is an issue with content retrieval
     */
    @NonNull
    public Word parse(Document document) throws IOException {
        var verb = new Verb();

        infinitive(verb, document);

        presentTense(verb, document);
        pastTense(verb, document);
        futureTense(verb, document);

        imperative(verb, document);

        form(verb, "bezosobnik", document);
        form(verb, "gerundium", document);
        form(verb, "imiesłów przysłówkowy współczesny", document);
        form(verb, "imiesłów przymiotnikowy czynny", document);
        form(verb, "imiesłów przymiotnikowy bierny", document);
        form(verb, "odpowiednik aspektowy", document);

        return verb;
    }

    /**
     * Assigns an infinitive to the word
     */
    private void infinitive(Verb verb, Document document) {
        Element h1 = document.selectFirst("h1");

        if (h1 == null) {
            log.warn("Unable to find an h1 tag on the page");
        } else {
            verb.setInfinitive(h1.text());
        }
    }

    /**
     * Assigns a present tense forms to the word
     */
    private void presentTense(Verb verb, Document document) {
        Table table = presentTenseTable(document);

        if (table == null) {
            log.warn("Unable to find a present tense table on the page");

            return;
        }

        verb.put("pojedyncza.teraźniejszy.first",   table.extract(1,0));
        verb.put("pojedyncza.teraźniejszy.second",  table.extract(2,0));
        verb.put("pojedyncza.teraźniejszy.third",   table.extract(3,0));
        verb.put("mnoga.teraźniejszy.first",        table.extract(1,1));
        verb.put("mnoga.teraźniejszy.second",       table.extract(2,1));
        verb.put("mnoga.teraźniejszy.third",        table.extract(3,1));
    }

    /**
     * Assigns a past tense forms to the word
     */
    private void pastTense(Verb verb, Document document) {
        Table pastTenseTable = table("Czas przeszły", document);

        if (pastTenseTable == null) {
            log.warn("Unable to find a past tense table on the page");

            return;
        }

        verb.put("pojedyncza.przeszły.męski.first",     pastTenseTable.extract(2, 0, "span.forma:eq(0)"));
        verb.put("pojedyncza.przeszły.męski.second",    pastTenseTable.extract(3, 0, "span.forma:eq(0)"));
        verb.put("pojedyncza.przeszły.męski.third",     pastTenseTable.extract(4, 0, "span.forma:eq(0)"));

        verb.put("pojedyncza.przeszły.żeński.first",    pastTenseTable.extract(2, 1, "span.forma:eq(0)"));
        verb.put("pojedyncza.przeszły.żeński.second",   pastTenseTable.extract(3, 1, "span.forma:eq(0)"));
        verb.put("pojedyncza.przeszły.żeński.third",    pastTenseTable.extract(4, 1, "span.forma:eq(0)"));

        verb.put("pojedyncza.przeszły.nijaki.first",    pastTenseTable.extract(2, 2, "span.forma:eq(0)"));
        verb.put("pojedyncza.przeszły.nijaki.second",   pastTenseTable.extract(3, 2, "span.forma:eq(0)"));
        verb.put("pojedyncza.przeszły.nijaki.third",    pastTenseTable.extract(4, 2, "span.forma:eq(0)"));

        verb.put("mnoga.przeszły.męski.first",          pastTenseTable.extract(2, 3, "span.forma:eq(0)"));
        verb.put("mnoga.przeszły.męski.second",         pastTenseTable.extract(3, 3, "span.forma:eq(0)"));
        verb.put("mnoga.przeszły.męski.third",          pastTenseTable.extract(4, 3, "span.forma:eq(0)"));

        verb.put("mnoga.przeszły.żeński.first",         pastTenseTable.extract(2, 4, "span.forma:eq(0)"));
        verb.put("mnoga.przeszły.żeński.second",        pastTenseTable.extract(3, 4, "span.forma:eq(0)"));
        verb.put("mnoga.przeszły.żeński.third",         pastTenseTable.extract(4, 4, "span.forma:eq(0)"));

        verb.put("mnoga.przeszły.nijaki.first",         pastTenseTable.extract(2, 4, "span.forma:eq(0)"));
        verb.put("mnoga.przeszły.nijaki.second",        pastTenseTable.extract(3, 4, "span.forma:eq(0)"));
        verb.put("mnoga.przeszły.nijaki.third",         pastTenseTable.extract(4, 4, "span.forma:eq(0)"));
    }

    /**
     * Assigns a future tense forms to the word
     */
    private void futureTense(Verb verb, Document document) {
        Table table = table("Czas przyszły", document);

        if (table == null) {
            log.warn("Unable to find a future tense table on the page");

            return;
        }

        verb.put("pojedyncza.przyszły.męski.first",         table.extract(2, 0, "span.forma:eq(0)"));
        verb.put("pojedyncza.przyszły.męski.second",        table.extract(3, 0, "span.forma:eq(0)"));
        verb.put("pojedyncza.przyszły.męski.third",         table.extract(4, 0, "span.forma:eq(0)"));

        verb.put("pojedyncza.przyszły.żeński.first",        table.extract(2, 1, "span.forma:eq(0)"));
        verb.put("pojedyncza.przyszły.żeński.second",       table.extract(3, 1, "span.forma:eq(0)"));
        verb.put("pojedyncza.przyszły.żeński.third",        table.extract(4, 1, "span.forma:eq(0)"));

        verb.put("pojedyncza.przyszły.nijaki.first",        table.extract(2, 2, "span.forma:eq(0)"));
        verb.put("pojedyncza.przyszły.nijaki.second",       table.extract(3, 2, "span.forma:eq(0)"));
        verb.put("pojedyncza.przyszły.nijaki.third",        table.extract(4, 2, "span.forma:eq(0)"));

        verb.put("mnoga.przyszły.męskoosobowy.first",       table.extract(2, 3, "span.forma:eq(0)"));
        verb.put("mnoga.przyszły.męskoosobowy.second",      table.extract(3, 3, "span.forma:eq(0)"));
        verb.put("mnoga.przyszły.męskoosobowy.third",       table.extract(4, 3, "span.forma:eq(0)"));

        verb.put("mnoga.przyszły.niemęskoosobowy.first",    table.extract(2, 4, "span.forma:eq(0)"));
        verb.put("mnoga.przyszły.niemęskoosobowy.second",   table.extract(3, 4, "span.forma:eq(0)"));
        verb.put("mnoga.przyszły.niemęskoosobowy.third",    table.extract(4, 4, "span.forma:eq(0)"));
    }

    /**
     * Assigns an imperative form to the verb
     * @param verb a verb
     * @param document a document to be parsed
     */
    private void imperative(Verb verb, Document document) {
        Table imperativeTable = table("Tryb rozkazujący", document);

        if (imperativeTable == null) {
            log.warn("Unable to find an imperative table on the page");

            return;
        }

        verb.put("pojedyncza.rozkazujący.second",   imperativeTable.extract(2, 0, "span.forma:eq(0)"));
        verb.put("mnoga.rozkazujący.first",         imperativeTable.extract(1, 0, "span.forma:eq(0)"));
        verb.put("mnoga.rozkazujący.second",        imperativeTable.extract(2, 1, "span.forma:eq(0)"));
    }

    /**
     * Assign different word forms to the verb
     * @param verb a verb
     * @param form a form
     * @param document a document to be parsed
     */
    private void form(Verb verb, String form, Document document) {
        Element p = document.selectFirst("p:contains(" + form + ":)");

        if (p == null) {
            log.warn("Unable to find '" + form + "' form on the page");

            return;
        }

        verb.put(form, StringUtils.substringAfter(p.text(), form + ": "));
    }

    /**
     * Returns the table with all the word's forms in the present tense
     * @param document a document to search for the table on
     * @return the present tense table, or <b>{@code null}</b> if not found
     */
    @Nullable
    private Table presentTenseTable(Document document) {
        Table table = table("Czas teraźniejszy", document);

        if (table != null) {
            return table;
        }

        // TODO: Recall why it is needed, add an example and integration test.
        // Probably, this is possible when the table exists but there is no direct header to be sure.
        // So trying to find present tense by indirect signs like the number of rows/columns
        Element element = document.selectFirst("table.table-striped.fleksja-table.table-bordered");

        if (element != null) {
            Elements headers = element.select("thead tr th");
            Elements rows = element.select("tbody tr");

            if (headers.size() == 3 && rows.size() == 3) {
                return Table.from(element);
            }
        }

        return null;
    }

    /**
     * Finds a table by the tense on the page.
     *
     * @param tense the tense i.e. teraźniejszy, przyszły, etc.
     * @param document the document to search the table on
     * @return the table that contains all the word's forms by the tense, or <b>{@code null}</b> if not found
     */
    @Nullable
    private Table table(String tense, Document document) {
        Element p = document.selectFirst("p:contains(" + tense + ")");

        if (p != null) {
            Element parent = p.parent();

            if (parent != null) {
                Elements children = parent.children();

                Element element = children.get(children.indexOf(p) + 1).selectFirst("table");

                if (element != null && "table".equals(element.tag().getName())) {
                    return Table.from(element);
                }
            }
        }

        return null;
    }
}
