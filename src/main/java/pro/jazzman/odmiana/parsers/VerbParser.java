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
            verb.setSingularPresent1(table.extract(1,0));
            verb.setSingularPresent2(table.extract(2, 0));
            verb.setSingularPresent3(table.extract(3, 0));
            verb.setPluralPresent1(table.extract(1, 1));
            verb.setPluralPresent2(table.extract(2, 1));
            verb.setPluralPresent3(table.extract(3, 1));
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
            verb.setSingularPastMale1(pastTenseTable.extract(2, 0, "span.forma:eq(0)"));
            verb.setSingularPastMale2(pastTenseTable.extract(3, 0, "span.forma:eq(0)"));
            verb.setSingularPastMale3(pastTenseTable.extract(4, 0, "span.forma:eq(0)"));

            verb.setSingularPastFemale1(pastTenseTable.extract(2, 1, "span.forma:eq(0)"));
            verb.setSingularPastFemale2(pastTenseTable.extract(3, 1, "span.forma:eq(0)"));
            verb.setSingularPastFemale3(pastTenseTable.extract(4, 1, "span.forma:eq(0)"));

            verb.setSingularPastNeutral1(pastTenseTable.extract(2, 2, "span.forma:eq(0)"));
            verb.setSingularPastNeutral2(pastTenseTable.extract(3, 2, "span.forma:eq(0)"));
            verb.setSingularPastNeutral3(pastTenseTable.extract(4, 2, "span.forma:eq(0)"));

            verb.setPluralPastMale1(pastTenseTable.extract(2, 3, "span.forma:eq(0)"));
            verb.setPluralPastMale2(pastTenseTable.extract(3, 3, "span.forma:eq(0)"));
            verb.setPluralPastMale3(pastTenseTable.extract(4, 3, "span.forma:eq(0)"));

            verb.setPluralPastFemale1(pastTenseTable.extract(2, 4, "span.forma:eq(0)"));
            verb.setPluralPastFemale2(pastTenseTable.extract(3, 4, "span.forma:eq(0)"));
            verb.setPluralPastFemale3(pastTenseTable.extract(4, 4, "span.forma:eq(0)"));

            verb.setPluralPastNeutral1(pastTenseTable.extract(2, 4, "span.forma:eq(0)"));
            verb.setPluralPastNeutral2(pastTenseTable.extract(3, 4, "span.forma:eq(0)"));
            verb.setPluralPastNeutral3(pastTenseTable.extract(4, 4, "span.forma:eq(0)"));
        }
    }

    private void setFuture() {
        Table table = futureTenseTable();

        if (table == null) {
            log.warn("Unable to find a future tense table on the page");

            return;
        }

        verb.setSingularFutureMale1(table.extract(2, 0, "span.forma:eq(0)"));
        verb.setSingularFutureMale2(table.extract(3, 0, "span.forma:eq(0)"));
        verb.setSingularFutureMale3(table.extract(4, 0, "span.forma:eq(0)"));
        verb.setSingularFutureFemale1(table.extract(2, 1, "span.forma:eq(0)"));
        verb.setSingularFutureFemale2(table.extract(3, 1, "span.forma:eq(0)"));
        verb.setSingularFutureFemale3(table.extract(4, 1, "span.forma:eq(0)"));
        verb.setSingularFutureNeutral1(table.extract(2, 2, "span.forma:eq(0)"));
        verb.setSingularFutureNeutral2(table.extract(3, 2, "span.forma:eq(0)"));
        verb.setSingularFutureNeutral3(table.extract(4, 2, "span.forma:eq(0)"));

        verb.setPluralFutureMale1(table.extract(2, 3, "span.forma:eq(0)"));
        verb.setPluralFutureMale2(table.extract(3, 3, "span.forma:eq(0)"));
        verb.setPluralFutureMale3(table.extract(4, 3, "span.forma:eq(0)"));
        verb.setPluralFutureNonMale1(table.extract(2, 4, "span.forma:eq(0)"));
        verb.setPluralFutureNonMale2(table.extract(3, 4, "span.forma:eq(0)"));
        verb.setPluralFutureNonMale3(table.extract(4, 4, "span.forma:eq(0)"));
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
            verb.setSingularImperative2(imperativeTable.extract(2, 0, "span.forma:eq(0)"));

            verb.setPluralImperative1(imperativeTable.extract(1, 0, "span.forma:eq(0)"));
            verb.setPluralImperative2(imperativeTable.extract(2, 1, "span.forma:eq(0)"));
        }
    }

    private void setImpersonal() {
        Element p = document.selectFirst("p:contains(bezosobnik:)");

        if (p == null) {
            log.warn("Unable to find impersonal form on the page");

            return;
        }

        verb.setImpersonal(StringUtils.substringAfter(p.text(), "bezosobnik: "));
    }

    private void setGerund() {
        Element p = document.selectFirst("p:contains(gerundium:)");

        if (p == null) {
            log.warn("Unable to find gerund form on the page");

            return;
        }

        verb.setGerund(StringUtils.substringAfter(p.text(), "gerundium: "));
    }

    private void setModernAdverbialParticiple() {
        final String PATTERN = "imiesłów przysłówkowy współczesny:";
        Element p = document.selectFirst("p:contains(" + PATTERN + ")");

        if (p == null) {
            log.warn("Unable to find modern adverbial participle form on the page");

            return;
        }

        verb.setModernAdverbialParticiple(StringUtils.substringAfter(p.text(), PATTERN));
    }

    private void setActiveParticiple() {
        final String PATTERN = "imiesłów przymiotnikowy czynny:";
        Element p = document.selectFirst("p:contains(" + PATTERN + ")");

        if (p == null) {
            log.warn("Unable to find active participle form on the page");

            return;
        }

        verb.setActiveParticiple(StringUtils.substringAfter(p.text(), PATTERN));
    }

    private void setPassiveAdjectiveParticiple() {
        final String PATTERN = "imiesłów przymiotnikowy bierny:";
        Element p = document.selectFirst("p:contains(" + PATTERN + ")");

        if (p == null) {
            log.warn("Unable to find active participle form on the page");

            return;
        }

        verb.setPassiveAdjectiveParticiple(StringUtils.substringAfter(p.text(), PATTERN));
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
