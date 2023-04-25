package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import pro.jazzman.odmiana.entities.partsofspeech.NounType;

import java.util.*;

@AllArgsConstructor
public class NounView extends View {
    private static final String HEADER = """
        `${base}`${translation}
        
        *Część mowy*: rzeczownik
        *Rodzaj*: ${type}
        """;

    private static final String CASES = """
        *Liczba pojedyncza* | *mnoga*
        ```
        M: ${singular.mianownik} | ${plural.mianownik}
        D: ${singular.dopelniacz} | ${plural.dopelniacz}
        C: ${singular.celownik} | ${plural.celownik}
        B: ${singular.biernik} | ${plural.biernik}
        N: ${singular.narzednik} | ${plural.narzednik}
        M: ${singular.miejscownik} | ${plural.miejscownik}
        W: ${singular.wolacz} | ${plural.wolacz}
        ```
        """;

    private final Noun noun;

    public String render() {
        String template = HEADER;
        var placeholders = new HashMap<String, String>();
        placeholders.put("translation", noun.hasTranslation() ? " - " + noun.getTranslation() : "");

        placeholders.put("base", noun.getBase());
        placeholders.put("type", noun.getType() != null ? noun.getType().inPolish() + emoji(noun.getType()) : "---");

        if (!noun.singulars().isEmpty() || !noun.plurals().isEmpty()) {
            template += System.lineSeparator() + CASES;

            int maxLength = maxLength(noun.singulars());

            placeholders.put("singular.mianownik", fixedString(noun.getSingularMianownik(), maxLength));
            placeholders.put("singular.dopelniacz", fixedString(noun.getSingularDopelniacz(), maxLength));
            placeholders.put("singular.celownik", fixedString(noun.getSingularCelownik(), maxLength));
            placeholders.put("singular.biernik", fixedString(noun.getSingularBiernik(), maxLength));
            placeholders.put("singular.narzednik", fixedString(noun.getSingularNarzednik(), maxLength));
            placeholders.put("singular.miejscownik", fixedString(noun.getSingularMiejscownik(), maxLength));
            placeholders.put("singular.wolacz", fixedString(noun.getSingularWolacz(), maxLength));

            placeholders.put("plural.mianownik", noun.getPluralMianownik());
            placeholders.put("plural.dopelniacz", noun.getPluralDopelniacz());
            placeholders.put("plural.celownik", noun.getPluralCelownik());
            placeholders.put("plural.biernik", noun.getPluralBiernik());
            placeholders.put("plural.narzednik", noun.getPluralNarzednik());
            placeholders.put("plural.miejscownik", noun.getPluralMiejscownik());
            placeholders.put("plural.wolacz", noun.getPluralWolacz());

            placeholders.replaceAll((k, v) -> v != null ? v : "-");
        }

        return StringSubstitutor.replace(template, placeholders);
    }

    private String emoji(NounType type) {
        return switch (type) {
            case MESKOOSOBOWY, MESKORZECZOWY, MESKOZYWOTNY -> "🧔🏼";
            case ZENSKI -> "👩🏼";
            case NIJAKI_1, NIJAKI_2 -> "🍏";
            case PRZYMNOGI_1, PRZYMNOGI_2 -> "";
        };
    }
}
