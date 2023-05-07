package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import pro.jazzman.odmiana.entities.partsofspeech.NounType;

import java.util.*;

@AllArgsConstructor
public class NounView extends View {
    private static final String TEMPLATE = """
        `${base}`
        
        *Część mowy*: rzeczownik
        *Rodzaj*: ${type}
        
        *Liczba pojedyncza* | *mnoga*
        ```
        M: ${pojedyncza.mianownik} | ${mnoga.mianownik}
        D: ${pojedyncza.dopelniacz} | ${mnoga.dopelniacz}
        C: ${pojedyncza.celownik} | ${mnoga.celownik}
        B: ${pojedyncza.biernik} | ${mnoga.biernik}
        N: ${pojedyncza.narzędnik} | ${mnoga.narzędnik}
        M: ${pojedyncza.miejscownik} | ${mnoga.miejscownik}
        W: ${pojedyncza.wolacz} | ${mnoga.wolacz}
        ```
        """;

    private final Noun noun;

    public String render() {
        var placeholders = new HashMap<String, String>();

        placeholders.put("base", noun.getBase());
        placeholders.put("type", noun.getType() != null ? noun.getType().inPolish() + emoji(noun.getType()) : "---");

        Map<String, String> singulars = noun.get("pojedyncza");
        Map<String, String> plurals = noun.get("mnoga");

        int length = maxLength(singulars.values().stream().toList());
        singulars.forEach((key, value) -> placeholders.put(key, fixedString(value, length)));

        placeholders.putAll(plurals);

        placeholders.replaceAll((k, v) -> v != null ? v : "-");

        return StringSubstitutor.replace(TEMPLATE, placeholders);
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
