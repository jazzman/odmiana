package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class VerbView implements View {
    private static final String TEMPLATE = """
        `${infinitive}`${translation}
            
        ⏰*Czas teraźniejszy* - *liczba pojedyncza* | *mnoga*
        ```
        1 os: ${singular.present.first} | ${plural.present.first}
        2 os: ${singular.present.second} | ${plural.present.second}
        3 os: ${singular.present.third} | ${plural.present.third}
        ```
        
        ⏰*Czas przeszły* - *liczba pojedyncza* | *mnoga*
        ```
        🧔🏼1 os: ${singular.past.male.first} | ${plural.past.male.first}
        🧔🏼‍2 os: ${singular.past.male.second} | ${plural.past.male.second}
        🧔🏼3 os: ${singular.past.male.third} | ${plural.past.male.third}
            
        👩🏼1 os: ${singular.past.female.first} | ${plural.past.female.first}
        👩🏼2 os: ${singular.past.female.second} | ${plural.past.female.second}
        👩🏼3 os: ${singular.past.female.third} | ${plural.past.female.third}
            
        🍏1 os: ${singular.past.neutral.first} | ${plural.past.neutral.first}
        🍏2 os: ${singular.past.neutral.second} | ${plural.past.neutral.second}
        🍏3 os: ${singular.past.neutral.third} | ${plural.past.neutral.third}
        ```
        
        """;

    private static final String FUTURE_TEMPLATE = """
        ⏰*Czas przyszły* - *liczba pojedyncza*
        ```
        🧔🏼1 os: ${singular.future.male.first}
        🧔🏼‍2 os: ${singular.future.male.second}
        🧔🏼3 os: ${singular.future.male.third}
        
        👩🏼1 os: ${singular.future.female.first}
        👩🏼2 os: ${singular.future.female.second}
        👩🏼3 os: ${singular.future.female.third}
        
        🍏1 os: ${singular.future.neutral.first}
        🍏2 os: ${singular.future.neutral.second}
        🍏3 os: ${singular.future.neutral.third}
        ```
        
        ⏰*Czas przyszły* - *liczba mnoga*
        ```
        🙎🏼‍1 os: ${plural.future.male.first}
        🙎🏼‍2 os: ${plural.future.male.second}
        🙎🏼‍3 os: ${plural.future.male.third}
        
        🙅🏼‍1 os: ${plural.future.nonmale.first}
        🙅🏼‍2 os: ${plural.future.nonmale.second}
        🙅🏼‍3 os: ${plural.future.nonmale.third}
        ```
        
        """;

    private static final String IMPERATIVE_TEMPLATE = """
        📢*Tryb rozkazujący*
        ```
        1 os: ${singular.imperative.placeholder} | ${plural.imperative.first}
        2 os: ${singular.imperative.second} | ${plural.imperative.second}
        ```
        
        """;

    private static final String ADDITIONAL_TEMPLATE = """
        *Bezosobnik*: `${impersonal}`
        *Gerundium*: `${gerund}`
        *Imiesłów przysłówkowy współczesny*: `${modern.adverbial.participle}`
        *Imiesłów przymiotnikowy czynny*: `${active.participle}`
        *Imiesłów przymiotnikowy bierny*: `${passive.adjective.participle}`
        """;
    private Verb verb;

    public String render() {
        String template = TEMPLATE;

        var placeholders = new HashMap<String, String>();
        placeholders.put("infinitive", verb.getInfinitive());
        placeholders.put("translation", verb.hasTranslation() ? " - " + verb.getTranslation() : "");

        int presentMaxLength = maxLength(verb.getSingularPresent1(), verb.getSingularPresent2(), verb.getSingularPresent3());

        placeholders.put("singular.present.first", fixedString(verb.getSingularPresent1(), presentMaxLength));
        placeholders.put("singular.present.second", fixedString(verb.getSingularPresent2(), presentMaxLength));
        placeholders.put("singular.present.third", fixedString(verb.getSingularPresent3(), presentMaxLength));
        placeholders.put("plural.present.first", verb.getPluralPresent1());
        placeholders.put("plural.present.second", verb.getPluralPresent2());
        placeholders.put("plural.present.third", verb.getPluralPresent3());

        int pastMaxLength = maxLength(
            verb.getSingularPastMale1(), verb.getSingularPastMale2(), verb.getSingularPastMale3(),
            verb.getSingularPastFemale1(), verb.getSingularPastFemale2(), verb.getSingularPastFemale3(),
            verb.getSingularPastNeutral1(), verb.getSingularPastNeutral2(), verb.getSingularPastNeutral3()
        );

        placeholders.put("singular.past.male.first", fixedString(verb.getSingularPastMale1(), pastMaxLength));
        placeholders.put("singular.past.male.second", fixedString(verb.getSingularPastMale2(), pastMaxLength));
        placeholders.put("singular.past.male.third", fixedString(verb.getSingularPastMale3(), pastMaxLength));
        placeholders.put("singular.past.female.first", fixedString(verb.getSingularPastFemale1(), pastMaxLength));
        placeholders.put("singular.past.female.second", fixedString(verb.getSingularPastFemale2(), pastMaxLength));
        placeholders.put("singular.past.female.third", fixedString(verb.getSingularPastFemale3(), pastMaxLength));
        placeholders.put("singular.past.neutral.first", fixedString(verb.getSingularPastNeutral1(), pastMaxLength));
        placeholders.put("singular.past.neutral.second", fixedString(verb.getSingularPastNeutral2(), pastMaxLength));
        placeholders.put("singular.past.neutral.third", fixedString(verb.getSingularPastNeutral3(), pastMaxLength));

        placeholders.put("plural.past.male.first", verb.getPluralPastMale1());
        placeholders.put("plural.past.male.second", verb.getPluralPastMale2());
        placeholders.put("plural.past.male.third", verb.getPluralPastMale3());
        placeholders.put("plural.past.female.first", verb.getPluralPastFemale1());
        placeholders.put("plural.past.female.second", verb.getPluralPastFemale2());
        placeholders.put("plural.past.female.third", verb.getPluralPastFemale3());
        placeholders.put("plural.past.neutral.first", verb.getPluralPastNeutral1());
        placeholders.put("plural.past.neutral.second", verb.getPluralPastNeutral2());
        placeholders.put("plural.past.neutral.third", verb.getPluralPastNeutral3());

        if (verb.getSingularFutureMale1() != null) {
            placeholders.put("singular.future.male.first", verb.getSingularFutureMale1());
            placeholders.put("singular.future.male.second", verb.getSingularFutureMale2());
            placeholders.put("singular.future.male.third", verb.getSingularFutureMale3());

            placeholders.put("singular.future.female.first", verb.getSingularFutureFemale1());
            placeholders.put("singular.future.female.second", verb.getSingularFutureFemale2());
            placeholders.put("singular.future.female.third", verb.getSingularFutureFemale3());

            placeholders.put("singular.future.neutral.first", verb.getSingularFutureNeutral1());
            placeholders.put("singular.future.neutral.second", verb.getSingularFutureNeutral2());
            placeholders.put("singular.future.neutral.third", verb.getSingularFutureNeutral3());

            placeholders.put("plural.future.male.first", verb.getPluralFutureMale1());
            placeholders.put("plural.future.male.second", verb.getPluralFutureMale2());
            placeholders.put("plural.future.male.third", verb.getPluralFutureMale3());

            placeholders.put("plural.future.nonmale.first", verb.getPluralFutureNonMale1());
            placeholders.put("plural.future.nonmale.second", verb.getPluralFutureNonMale2());
            placeholders.put("plural.future.nonmale.third", verb.getPluralFutureNonMale3());

            template += FUTURE_TEMPLATE;
        }

        if (verb.getPluralImperative1() != null) {
            placeholders.put("singular.imperative.placeholder", "-".repeat(verb.getSingularImperative2().length()));
            placeholders.put("singular.imperative.second", verb.getSingularImperative2());
            placeholders.put("plural.imperative.first", verb.getPluralImperative1());
            placeholders.put("plural.imperative.second", verb.getPluralImperative2());

            template += IMPERATIVE_TEMPLATE;
        }

        if (verb.getImpersonal() != null) {
            placeholders.put("impersonal", verb.getImpersonal());
            placeholders.put("gerund", verb.getGerund());
            placeholders.put("modern.adverbial.participle", verb.getModernAdverbialParticiple());
            placeholders.put("active.participle", verb.getActiveParticiple());
            placeholders.put("passive.adjective.participle", verb.getPassiveAdjectiveParticiple());

            template += ADDITIONAL_TEMPLATE;
        }


        placeholders.replaceAll((k, v) -> v != null ? v : "-");

        return StringSubstitutor.replace(template, placeholders);
    }

    private String fixedString(String word, int length) {
        return StringUtils.rightPad(word != null ? word : "", length);
    }

    private int maxLength(String... words) {
        return Arrays.stream(words)
            .filter(Objects::nonNull)
            .max(Comparator.comparing(String::length))
            .map(String::length).orElse(0);
    }
}
