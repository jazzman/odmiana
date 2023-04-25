package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Adjective;

import java.util.*;

@AllArgsConstructor
public class AdjectiveView extends View {
    private static final String HEADER = """
        `${base}`${translation}
        
        *Część mowy*: przymiotnik
        
        *Stopień równy*
        
        """;

    private static final String STOPIEN_ROWNY_MESKOOSOBOWY = """
        *🧔🏼Osoby i 🐱Zwierzęta* | *🙎🏼‍Męskoosobowy*
        ```
        M: ${singular.mianownik.male} | ${plural.mianownik.male}
        D: ${singular.dopelniacz.male} | ${plural.dopelniacz.male}
        C: ${singular.celownik.male} | ${plural.celownik.male}
        B: ${singular.biernik.male} | ${plural.biernik.male}
        N: ${singular.narzednik.male} | ${plural.narzednik.male}
        M: ${singular.miejscownik.male} | ${plural.miejscownik.male}
        W: ${singular.wolacz.male} | ${plural.wolacz.male}
        ```
        
        """;

    private static final String STOPIEN_ROWNY_MESKORZECZOWY = """
        *🏠Rzeczy* | *🙎🏼‍Męskoosobowy*
        ```
        M: ${singular.mianownik.male.notalive} | ${plural.mianownik.male}
        D: ${singular.dopelniacz.male.notalive} | ${plural.dopelniacz.male}
        C: ${singular.celownik.male.notalive} | ${plural.celownik.male}
        B: ${singular.biernik.male.notalive} | ${plural.biernik.male}
        N: ${singular.narzednik.male.notalive} | ${plural.narzednik.male}
        M: ${singular.miejscownik.male.notalive} | ${plural.miejscownik.male}
        W: ${singular.wolacz.male.notalive} | ${plural.wolacz.male}
        ```
        
        """;

    private static final String STOPIEN_ROWNY_ZENSKI = """
        *👩🏼Żeński* | *🙅🏼‍Niemęskoosobowy*
        ```
        M: ${singular.mianownik.female} | ${plural.mianownik.nonmale}
        D: ${singular.dopelniacz.female} | ${plural.dopelniacz.nonmale}
        C: ${singular.celownik.female} | ${plural.celownik.nonmale}
        B: ${singular.biernik.female} | ${plural.biernik.nonmale}
        N: ${singular.narzednik.female} | ${plural.narzednik.nonmale}
        M: ${singular.miejscownik.female} | ${plural.miejscownik.nonmale}
        W: ${singular.wolacz.female} | ${plural.wolacz.nonmale}
        ```
        
        """;

    private static final String STOPIEN_ROWNY_NIJAKI = """
        *🍏Nijaki* | *🙅🏼‍Niemęskoosobowy*
        ```
        M: ${singular.mianownik.neutral} | ${plural.mianownik.nonmale}
        D: ${singular.dopelniacz.neutral} | ${plural.dopelniacz.nonmale}
        C: ${singular.celownik.neutral} | ${plural.celownik.nonmale}
        B: ${singular.biernik.neutral} | ${plural.biernik.nonmale}
        N: ${singular.narzednik.neutral} | ${plural.narzednik.nonmale}
        M: ${singular.miejscownik.neutral} | ${plural.miejscownik.nonmale}
        W: ${singular.wolacz.neutral} | ${plural.wolacz.nonmale}
        ```
        
        """;

    private Adjective adjective;

    public String render() {
        String template = HEADER;

        var placeholders = new HashMap<String, String>();

        placeholders.put("base", adjective.getBase());
        placeholders.put("translation", adjective.hasTranslation() ? " - " + adjective.getTranslation() : "");

        if (adjective.getSingularMaleMianownik() != null) {
            template += STOPIEN_ROWNY_MESKOOSOBOWY;

            int maxLength = maxLength(
                adjective.getSingularMaleMianownik(), adjective.getSingularMaleDopelniacz(), adjective.getSingularMaleCelownik(),
                adjective.getSingularMaleBiernik(), adjective.getSingularMaleNarzednik(), adjective.getSingularMaleMiejscownik(),
                adjective.getSingularMaleWolacz()
            );

            placeholders.put("singular.mianownik.male", fixedString(adjective.getSingularMaleMianownik(), maxLength));
            placeholders.put("singular.dopelniacz.male", fixedString(adjective.getSingularMaleDopelniacz(), maxLength));
            placeholders.put("singular.celownik.male", fixedString(adjective.getSingularMaleCelownik(), maxLength));
            placeholders.put("singular.biernik.male", fixedString(adjective.getSingularMaleBiernik(), maxLength));
            placeholders.put("singular.narzednik.male", fixedString(adjective.getSingularMaleNarzednik(), maxLength));
            placeholders.put("singular.miejscownik.male", fixedString(adjective.getSingularMaleMiejscownik(), maxLength));
            placeholders.put("singular.wolacz.male", fixedString(adjective.getSingularMaleWolacz(), maxLength));

            placeholders.put("plural.mianownik.male", adjective.getPluralMaleMianownik());
            placeholders.put("plural.dopelniacz.male", adjective.getPluralMaleDopelniacz());
            placeholders.put("plural.celownik.male", adjective.getPluralMaleCelownik());
            placeholders.put("plural.biernik.male", adjective.getPluralMaleBiernik());
            placeholders.put("plural.narzednik.male", adjective.getPluralMaleNarzednik());
            placeholders.put("plural.miejscownik.male", adjective.getPluralMaleMiejscownik());
            placeholders.put("plural.wolacz.male", adjective.getPluralMaleWolacz());
        }

        if (adjective.getSingularMaleNotAliveMianownik() != null) {
            template += STOPIEN_ROWNY_MESKORZECZOWY;

            int maxLength = maxLength(
                adjective.getSingularMaleNotAliveMianownik(), adjective.getSingularMaleNotAliveDopelniacz(), adjective.getSingularMaleNotAliveCelownik(),
                adjective.getSingularMaleNotAliveBiernik(), adjective.getSingularMaleNotAliveNarzednik(), adjective.getSingularMaleNotAliveMiejscownik(),
                adjective.getSingularMaleNotAliveWolacz()
            );

            placeholders.put("singular.mianownik.male.notalive", fixedString(adjective.getSingularMaleNotAliveMianownik(), maxLength));
            placeholders.put("singular.dopelniacz.male.notalive", fixedString(adjective.getSingularMaleNotAliveDopelniacz(), maxLength));
            placeholders.put("singular.celownik.male.notalive", fixedString(adjective.getSingularMaleNotAliveCelownik(), maxLength));
            placeholders.put("singular.biernik.male.notalive", fixedString(adjective.getSingularMaleNotAliveBiernik(), maxLength));
            placeholders.put("singular.narzednik.male.notalive", fixedString(adjective.getSingularMaleNotAliveNarzednik(), maxLength));
            placeholders.put("singular.miejscownik.male.notalive", fixedString(adjective.getSingularMaleNotAliveMiejscownik(), maxLength));
            placeholders.put("singular.wolacz.male.notalive", fixedString(adjective.getSingularMaleNotAliveWolacz(), maxLength));
        }

        if (adjective.getSingularFemaleMianownik() != null) {
            template += STOPIEN_ROWNY_ZENSKI;

            int maxLength = maxLength(
                adjective.getSingularFemaleMianownik(), adjective.getSingularFemaleDopelniacz(), adjective.getSingularFemaleCelownik(),
                adjective.getSingularFemaleBiernik(), adjective.getSingularFemaleNarzednik(), adjective.getSingularFemaleMiejscownik(),
                adjective.getSingularFemaleWolacz()
            );

            placeholders.put("singular.mianownik.female", fixedString(adjective.getSingularFemaleMianownik(), maxLength));
            placeholders.put("singular.dopelniacz.female", fixedString(adjective.getSingularFemaleDopelniacz(), maxLength));
            placeholders.put("singular.celownik.female", fixedString(adjective.getSingularFemaleCelownik(), maxLength));
            placeholders.put("singular.biernik.female", fixedString(adjective.getSingularFemaleBiernik(), maxLength));
            placeholders.put("singular.narzednik.female", fixedString(adjective.getSingularFemaleNarzednik(), maxLength));
            placeholders.put("singular.miejscownik.female", fixedString(adjective.getSingularFemaleMiejscownik(), maxLength));
            placeholders.put("singular.wolacz.female", fixedString(adjective.getSingularFemaleWolacz(), maxLength));

            placeholders.put("plural.mianownik.nonmale", adjective.getPluralNonMaleMianownik());
            placeholders.put("plural.dopelniacz.nonmale", adjective.getPluralNonMaleDopelniacz());
            placeholders.put("plural.celownik.nonmale", adjective.getPluralNonMaleCelownik());
            placeholders.put("plural.biernik.nonmale", adjective.getPluralNonMaleBiernik());
            placeholders.put("plural.narzednik.nonmale", adjective.getPluralNonMaleNarzednik());
            placeholders.put("plural.miejscownik.nonmale", adjective.getPluralNonMaleMiejscownik());
            placeholders.put("plural.wolacz.nonmale", adjective.getPluralNonMaleWolacz());
        }

        if (adjective.getSingularFemaleMianownik() != null) {
            template += STOPIEN_ROWNY_NIJAKI;

            int maxLength = maxLength(
                adjective.getSingularNeutralMianownik(), adjective.getSingularNeutralDopelniacz(), adjective.getSingularNeutralCelownik(),
                adjective.getSingularNeutralBiernik(), adjective.getSingularNeutralNarzednik(), adjective.getSingularNeutralMiejscownik(),
                adjective.getSingularNeutralWolacz()
            );

            placeholders.put("singular.mianownik.neutral", fixedString(adjective.getSingularNeutralMianownik(), maxLength));
            placeholders.put("singular.dopelniacz.neutral", fixedString(adjective.getSingularNeutralDopelniacz(), maxLength));
            placeholders.put("singular.celownik.neutral", fixedString(adjective.getSingularNeutralCelownik(), maxLength));
            placeholders.put("singular.biernik.neutral", fixedString(adjective.getSingularNeutralBiernik(), maxLength));
            placeholders.put("singular.narzednik.neutral", fixedString(adjective.getSingularNeutralNarzednik(), maxLength));
            placeholders.put("singular.miejscownik.neutral", fixedString(adjective.getSingularNeutralMiejscownik(), maxLength));
            placeholders.put("singular.wolacz.neutral", fixedString(adjective.getSingularNeutralWolacz(), maxLength));
        }

        placeholders.replaceAll((k, v) -> v != null ? v : "-");

        return StringSubstitutor.replace(template, placeholders);
    }
}
