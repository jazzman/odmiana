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

    public Word parse(Document document) throws IOException {
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

        verb.setSingularPresent1(present.textInCell(0));
        verb.setSingularPresent2(present.textInCell(1));
        verb.setSingularPresent3(present.textInCell(2));
        verb.setSingularPastMale1(pastM.textInCell(0));
        verb.setSingularPastMale2(pastM.textInCell(1));
        verb.setSingularPastMale3(pastM.textInCell(2));
        verb.setSingularPastFemale1(pastF.textInCell(0));
        verb.setSingularPastFemale2(pastF.textInCell(1));
        verb.setSingularPastFemale3(pastF.textInCell(2));
        verb.setSingularPastNeutral1(pastN.textInCell(0));
        verb.setSingularPastNeutral2(pastN.textInCell(1));
        verb.setSingularPastNeutral3(pastN.textInCell(2));
        verb.setSingularImperative1(imperative.textInCell(0));
        verb.setSingularImperative2(imperative.textInCell(1));
        verb.setSingularImperative3(imperative.textInCell(2));

        verb.setPluralPresent1(present.textInCell(3));
        verb.setPluralPresent2(present.textInCell(4));
        verb.setPluralPresent3(present.textInCell(5));
        verb.setPluralPastMale1(pastM.textInCell(3));
        verb.setPluralPastMale2(pastM.textInCell(4));
        verb.setPluralPastMale3(pastM.textInCell(5));
        verb.setPluralPastFemale1(pastF.textInCell(3));
        verb.setPluralPastFemale2(pastF.textInCell(4));
        verb.setPluralPastFemale3(pastF.textInCell(5));
        verb.setPluralPastNeutral1(pastF.textInCell(3));
        verb.setPluralPastNeutral2(pastF.textInCell(4));
        verb.setPluralPastNeutral3(pastF.textInCell(5));
        verb.setPluralImperative1(imperative.textInCell(3));
        verb.setPluralImperative2(imperative.textInCell(4));
        verb.setPluralImperative3(imperative.textInCell(5));

        return verb;
    }

    private String infinitive(Table table) throws NotFoundException, IOException {
        var infinitive = table.rows().row(2).cells().cell(0).selectFirst("b");

        if (infinitive == null) {
            throw new NotFoundException("Unable to find an infinitive");
        }

        return infinitive.text();
    }
}
