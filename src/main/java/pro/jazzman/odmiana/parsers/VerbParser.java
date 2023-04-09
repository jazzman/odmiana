package pro.jazzman.odmiana.parsers;

import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;
import pro.jazzman.odmiana.services.elements.Table;
import java.io.IOException;

@Slf4j
public class VerbParser implements Parser {
    private static final String TABLE_SELECTOR = "table.wikitable";

    public Word parse(Document document) throws Exception {
        Element element = document.selectFirst(TABLE_SELECTOR);

        if (element == null) {
            throw new IOException("Unable to get table for the verb by the following selector: '" + TABLE_SELECTOR + "'");
        }

        var table = Table.from(element);

        var rows = table.rows();
        var present = rows.row(3).cells();
        var pastM = rows.row(4).cells();
        var pastF = rows.row(5).cells();
        var pastN = rows.row(6).cells();
        var imperative = rows.row(7).cells();

        Verb verb = new Verb();
        verb.setInfinitive(infinitive(table));

        verb.setSingularPresent1(present.cell(0) != null ? present.cell(0).text() : "");
        verb.setSingularPresent2(present.cell(1) != null ? present.cell(1).text() : "");
        verb.setSingularPresent3(present.cell(2) != null ? present.cell(2).text() : "");
        verb.setSingularPastMale1(pastM.cell(0) != null ? pastM.cell(0).text() : "");
        verb.setSingularPastMale2(pastM.cell(1) != null ? pastM.cell(1).text() : "");
        verb.setSingularPastMale3(pastM.cell(2) != null ? pastM.cell(2).text() : "");
        verb.setSingularPastFemale1(pastF.cell(0) != null ? pastF.cell(0).text() : "");
        verb.setSingularPastFemale2(pastF.cell(1) != null ? pastF.cell(1).text() : "");
        verb.setSingularPastFemale3(pastF.cell(2) != null ? pastF.cell(2).text() : "");
        verb.setSingularPastNeutral1(pastN.cell(0) != null ? pastN.cell(0).text() : "");
        verb.setSingularPastNeutral2(pastN.cell(1) != null ? pastN.cell(1).text() : "");
        verb.setSingularPastNeutral3(pastN.cell(2) != null ? pastN.cell(2).text() : "");
        verb.setSingularImperative1(imperative.cell(0) != null ? imperative.cell(0).text() : "");
        verb.setSingularImperative2(imperative.cell(1) != null ? imperative.cell(1).text() : "");
        verb.setSingularImperative3(imperative.cell(2) != null ? imperative.cell(2).text() : "");

        verb.setPluralPresent1(present.cell(3) != null ? present.cell(3).text() : "");
        verb.setPluralPresent2(present.cell(4) != null ? present.cell(4).text() : "");
        verb.setPluralPresent3(present.cell(5) != null ? present.cell(5).text() : "");
        verb.setPluralPastMale1(pastM.cell(3) != null ? pastM.cell(3).text() : "");
        verb.setPluralPastMale2(pastM.cell(4) != null ? pastM.cell(4).text() : "");
        verb.setPluralPastMale3(pastM.cell(5) != null ? pastM.cell(5).text() : "");
        verb.setPluralPastFemale1(pastF.cell(3) != null ? pastF.cell(3).text() : "");
        verb.setPluralPastFemale2(pastF.cell(4) != null ? pastF.cell(4).text() : "");
        verb.setPluralPastFemale3(pastF.cell(5) != null ? pastF.cell(5).text() : "");
        verb.setPluralPastNeutral1(pastF.cell(3) != null ? pastF.cell(3).text() : "");
        verb.setPluralPastNeutral2(pastF.cell(4) != null ? pastF.cell(4).text() : "");
        verb.setPluralPastNeutral3(pastF.cell(5) != null ? pastF.cell(5).text() : "");
        verb.setPluralImperative1(imperative.cell(3) != null ? imperative.cell(3).text() : "");
        verb.setPluralImperative2(imperative.cell(4) != null ? imperative.cell(4).text() : "");
        verb.setPluralImperative3(imperative.cell(5) != null ? imperative.cell(5).text() : "");

        return verb;
    }

    private String infinitive(Table table) throws Exception {
        var infinitive = table.rows().row(2).cells().cell(0).selectFirst("b");

        if (infinitive == null) {
            throw new NotFoundException("Unable to find an infinitive");
        }

        return infinitive.text();
    }
}
