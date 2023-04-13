package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;

import java.util.HashMap;

@AllArgsConstructor
public class VerbView implements View {
    private static final String TEMPLATE = """
        *${infinitive}*${translation}
            
        *Liczba Pojedyncza* | *Mnoga*
            
        ⌛*Czas teraźniejszy*:
            
        1 os. - ${singular.present.first} | ${plural.present.first}
        2 os. - ${singular.present.second} | ${plural.present.second}
        3 os. - ${singular.present.third} | ${plural.present.third}
            
        ⌛*Czas przeszły*:
            
        🧔🏼1 os. - ${singular.past.male.first} | ${plural.past.male.first}
        🧔🏼‍2 os. - ${singular.past.male.second} | ${plural.past.male.second}
        🧔🏼3 os. - ${singular.past.male.third} | ${plural.past.male.third}
            
        👩🏼1 os. - ${singular.past.female.first} | ${plural.past.female.first}
        👩🏼2 os. - ${singular.past.female.second} | ${plural.past.female.second}
        👩🏼3 os. - ${singular.past.female.third} | ${plural.past.female.third}
            
        🍏1 os. - ${singular.past.neutral.first} | ${plural.past.neutral.first}
        🍏2 os. - ${singular.past.neutral.second} | ${plural.past.neutral.second}
        🍏3 os. - ${singular.past.neutral.third} | ${plural.past.neutral.third}
            
        *Tryb rozkazujący*:
            
        1 os. - ${singular.imperative.first} | ${plural.imperative.first}
        2 os. - ${singular.imperative.second} | ${plural.imperative.second}
        3 os. - ${singular.imperative.third} | ${plural.imperative.third}
        """;

    private Verb verb;

    public String render(String higlighted) {
        var placeholders = new HashMap<String, String>();
        placeholders.put("infinitive", verb.getInfinitive());
        placeholders.put("translation", verb.hasTranslation() ? " - " + verb.getTranslation() : "");
        placeholders.put("singular.present.first", verb.getSingularPresent1());
        placeholders.put("singular.present.second", verb.getSingularPresent2());
        placeholders.put("singular.present.third", verb.getSingularPresent3());
        placeholders.put("singular.past.male.first", verb.getSingularPastMale1());
        placeholders.put("singular.past.male.second", verb.getSingularPastMale2());
        placeholders.put("singular.past.male.third", verb.getSingularPastMale3());
        placeholders.put("singular.past.female.first", verb.getSingularPastFemale1());
        placeholders.put("singular.past.female.second", verb.getSingularPastFemale2());
        placeholders.put("singular.past.female.third", verb.getSingularPastFemale3());
        placeholders.put("singular.past.neutral.first", verb.getSingularPastNeutral1());
        placeholders.put("singular.past.neutral.second", verb.getSingularPastNeutral2());
        placeholders.put("singular.past.neutral.third", verb.getSingularPastNeutral3());
        placeholders.put("singular.imperative.first", verb.getSingularImperative1());
        placeholders.put("singular.imperative.second", verb.getSingularImperative2());
        placeholders.put("singular.imperative.third", verb.getSingularImperative3());
        placeholders.put("plural.present.first", verb.getPluralPresent1());
        placeholders.put("plural.present.second", verb.getPluralPresent2());
        placeholders.put("plural.present.third", verb.getPluralPresent3());
        placeholders.put("plural.past.male.first", verb.getPluralPastMale1());
        placeholders.put("plural.past.male.second", verb.getPluralPastMale2());
        placeholders.put("plural.past.male.third", verb.getPluralPastMale3());
        placeholders.put("plural.past.female.first", verb.getPluralPastFemale1());
        placeholders.put("plural.past.female.second", verb.getPluralPastFemale2());
        placeholders.put("plural.past.female.third", verb.getPluralPastFemale3());
        placeholders.put("plural.past.neutral.first", verb.getPluralPastNeutral1());
        placeholders.put("plural.past.neutral.second", verb.getPluralPastNeutral2());
        placeholders.put("plural.past.neutral.third", verb.getPluralPastNeutral3());
        placeholders.put("plural.imperative.first", verb.getPluralImperative1());
        placeholders.put("plural.imperative.second", verb.getPluralImperative2());
        placeholders.put("plural.imperative.third", verb.getPluralImperative3());

        placeholders.replaceAll((k, v) -> v != null && v.equals(higlighted) ? "*" + v + "* 👈" : v);

        return StringSubstitutor.replace(TEMPLATE, placeholders);
    }
}
