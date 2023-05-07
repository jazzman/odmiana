package pro.jazzman.odmiana.parsers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class VerbParser implements Parser {
    private final Document document;
    private final Verb verb = new Verb();

    public Word parse() throws IOException {
        setInfinitive();
        setPresent();
        setPast();
        setFuture();
        setImperative();
        setImpersonal();
        setGerund();
        setModernAdverbialParticiple();
        setActiveParticiple();
        setPassiveAdjectiveParticiple();

        return verb;
    }

    private void setInfinitive() {
        Element h1 = document.selectFirst("h1");

        if (h1 == null) {
            log.warn("Unable to find an h1 tag on the page");
        } else {
            verb.setInfinitive(h1.text());
        }
    }

    private void setPresent() {
        Table table = presentTenseTable(document);

        if (table == null) {
            log.warn("Unable to find a present tense table on the page");
        } else {
            verb.put("pojedyncza.teraźniejszy.first",   table.extract(1,0));
            verb.put("pojedyncza.teraźniejszy.second",  table.extract(2,0));
            verb.put("pojedyncza.teraźniejszy.third",   table.extract(3,0));
            verb.put("mnoga.teraźniejszy.first",        table.extract(1,1));
            verb.put("mnoga.teraźniejszy.second",       table.extract(2,1));
            verb.put("mnoga.teraźniejszy.third",        table.extract(3,1));
        }
    }

    private Table presentTenseTable(Document document) {
        Table table = table("Czas teraźniejszy", document);

        if (table != null) {
            return table;
        }

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

    private void setPast() {
        Table pastTenseTable = table("Czas przeszły", document);

        if (pastTenseTable == null) {
            log.warn("Unable to find a past tense table on the page");
        } else {
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
    }

    private void setFuture() {
        Table table = futureTenseTable();

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

    private Table futureTenseTable() {
        Element p = document.selectFirst("p:contains(Czas przyszły)");

        if (p != null) {
            Element parent = p.parent();

            if (parent != null) {
                Elements children = parent.children();

                Element element = children.get(children.indexOf(p) + 1).selectFirst("table");

                if (element != null) {
                    Elements headers = element.select("thead tr:eq(1) th");
                    Elements rows = element.select("tbody tr");

                    if (headers.size() == 5 && rows.size() == 3) {
                        return Table.from(element);
                    }
                }
            }
        }

        return null;
    }

    private void setImperative() {
        Table imperativeTable = table("Tryb rozkazujący", document);

        if (imperativeTable == null) {
            log.warn("Unable to find an imperative table on the page");
        } else {
            verb.put("pojedyncza.rozkazujący.second",   imperativeTable.extract(2, 0, "span.forma:eq(0)"));
            verb.put("mnoga.rozkazujący.first",         imperativeTable.extract(1, 0, "span.forma:eq(0)"));
            verb.put("mnoga.rozkazujący.second",        imperativeTable.extract(2, 1, "span.forma:eq(0)"));
        }
    }

    private void setImpersonal() {
        Element p = document.selectFirst("p:contains(bezosobnik:)");

        if (p == null) {
            log.warn("Unable to find impersonal form on the page");

            return;
        }

        verb.put("bezosobnik", StringUtils.substringAfter(p.text(), "bezosobnik: "));
    }

    private void setGerund() {
        Element p = document.selectFirst("p:contains(gerundium:)");

        if (p == null) {
            log.warn("Unable to find gerund form on the page");

            return;
        }

        verb.put("gerundium", StringUtils.substringAfter(p.text(), "gerundium: "));
    }

    private void setModernAdverbialParticiple() {
        Element p = document.selectFirst("p:contains(imiesłów przysłówkowy współczesny:)");

        if (p == null) {
            log.warn("Unable to find modern adverbial participle form on the page");

            return;
        }

        verb.put("imiesłów przysłówkowy współczesny", StringUtils.substringAfter(p.text(), "imiesłów przysłówkowy współczesny: "));
    }

    private void setActiveParticiple() {
        Element p = document.selectFirst("p:contains(imiesłów przymiotnikowy czynny:)");

        if (p == null) {
            log.warn("Unable to find active participle form on the page");

            return;
        }

        verb.put("imiesłów przymiotnikowy czynny", StringUtils.substringAfter(p.text(), "imiesłów przymiotnikowy czynny: "));
    }

    private void setPassiveAdjectiveParticiple() {
        final String PATTERN = "imiesłów przymiotnikowy bierny";
        Element p = document.selectFirst("p:contains(imiesłów przymiotnikowy bierny:)");

        if (p == null) {
            log.warn("Unable to find active participle form on the page");

            return;
        }

        verb.put("imiesłów przymiotnikowy bierny", StringUtils.substringAfter(p.text(), "imiesłów przymiotnikowy bierny: "));
    }

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
