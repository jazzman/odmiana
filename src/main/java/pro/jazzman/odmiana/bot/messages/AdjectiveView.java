package pro.jazzman.odmiana.bot.messages;

import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Adjective;

import java.util.*;

@RequiredArgsConstructor
public class AdjectiveView extends View {
    private static final String HEADER = """
        `${base}`
        
        *Część mowy*: przymiotnik
        
        """;

    private static final String MASCULINE = """
        *Stopień równy*
        
        *🧔🏼Osoby i 🐱Zwierzęta* | *🙎🏼‍Męskoosobowy*
        ```
        M: ${pojedyncza.męskoosobowy.mianownik} | ${mnoga.męskoosobowy.mianownik}
        D: ${pojedyncza.męskoosobowy.dopelniacz} | ${mnoga.męskoosobowy.dopelniacz}
        C: ${pojedyncza.męskoosobowy.celownik} | ${mnoga.męskoosobowy.celownik}
        B: ${pojedyncza.męskoosobowy.biernik} | ${mnoga.męskoosobowy.biernik}
        N: ${pojedyncza.męskoosobowy.narzędnik} | ${mnoga.męskoosobowy.narzędnik}
        M: ${pojedyncza.męskoosobowy.miejscownik} | ${mnoga.męskoosobowy.miejscownik}
        W: ${pojedyncza.męskoosobowy.wolacz} | ${mnoga.męskoosobowy.wolacz}
        ```
        
        """;

    private static final String MASCULINE_INANIMATE = """
        *🏠Rzeczy* | *🙎🏼‍Męskoosobowy*
        ```
        M: ${pojedyncza.męskorzeczowy.mianownik} | ${mnoga.męskoosobowy.mianownik}
        D: ${pojedyncza.męskorzeczowy.dopelniacz} | ${mnoga.męskoosobowy.dopelniacz}
        C: ${pojedyncza.męskorzeczowy.celownik} | ${mnoga.męskoosobowy.celownik}
        B: ${pojedyncza.męskorzeczowy.biernik} | ${mnoga.męskoosobowy.biernik}
        N: ${pojedyncza.męskorzeczowy.narzędnik} | ${mnoga.męskoosobowy.narzędnik}
        M: ${pojedyncza.męskorzeczowy.miejscownik} | ${mnoga.męskoosobowy.miejscownik}
        W: ${pojedyncza.męskorzeczowy.wolacz} | ${mnoga.męskoosobowy.wolacz}
        ```
        
        """;

    private static final String FEMININE = """
        *👩🏼Żeński* | *🙅🏼‍Niemęskoosobowy*
        ```
        M: ${pojedyncza.żeński.mianownik} | ${mnoga.niemęskoosobowy.mianownik}
        D: ${pojedyncza.żeński.dopelniacz} | ${mnoga.niemęskoosobowy.dopelniacz}
        C: ${pojedyncza.żeński.celownik} | ${mnoga.niemęskoosobowy.celownik}
        B: ${pojedyncza.żeński.biernik} | ${mnoga.niemęskoosobowy.biernik}
        N: ${pojedyncza.żeński.narzędnik} | ${mnoga.niemęskoosobowy.narzędnik}
        M: ${pojedyncza.żeński.miejscownik} | ${mnoga.niemęskoosobowy.miejscownik}
        W: ${pojedyncza.żeński.wolacz} | ${mnoga.niemęskoosobowy.wolacz}
        ```
        
        """;

    private static final String NEUTER = """
        *🍏Nijaki* | *🙅🏼‍Niemęskoosobowy*
        ```
        M: ${pojedyncza.nijaki.mianownik} | ${mnoga.niemęskoosobowy.mianownik}
        D: ${pojedyncza.nijaki.dopelniacz} | ${mnoga.niemęskoosobowy.dopelniacz}
        C: ${pojedyncza.nijaki.celownik} | ${mnoga.niemęskoosobowy.celownik}
        B: ${pojedyncza.nijaki.biernik} | ${mnoga.niemęskoosobowy.biernik}
        N: ${pojedyncza.nijaki.narzędnik} | ${mnoga.niemęskoosobowy.narzędnik}
        M: ${pojedyncza.nijaki.miejscownik} | ${mnoga.niemęskoosobowy.miejscownik}
        W: ${pojedyncza.nijaki.wolacz} | ${mnoga.niemęskoosobowy.wolacz}
        ```
        
        """;

    private static final String COMPARATIVE_MASCULINE = """
        *Stopień wyższy*
        
        *🧔🏼Osoby i 🐱Zwierzęta* | *🙎🏼‍Męskoosobowy*
        ```
        M: ${wyższy.pojedyncza.męskoosobowy.mianownik} | ${wyższy.mnoga.męskoosobowy.mianownik}
        D: ${wyższy.pojedyncza.męskoosobowy.dopelniacz} | ${wyższy.mnoga.męskoosobowy.dopelniacz}
        C: ${wyższy.pojedyncza.męskoosobowy.celownik} | ${wyższy.mnoga.męskoosobowy.celownik}
        B: ${wyższy.pojedyncza.męskoosobowy.biernik} | ${wyższy.mnoga.męskoosobowy.biernik}
        N: ${wyższy.pojedyncza.męskoosobowy.narzędnik} | ${wyższy.mnoga.męskoosobowy.narzędnik}
        M: ${wyższy.pojedyncza.męskoosobowy.miejscownik} | ${wyższy.mnoga.męskoosobowy.miejscownik}
        ```
        
        """;

    private static final String COMPARATIVE_MASCULINE_INANIMATE = """
        *🏠Rzeczy* | *🙎🏼‍Męskoosobowy*
        ```
        M: ${wyższy.pojedyncza.męskorzeczowy.mianownik} | ${wyższy.mnoga.męskoosobowy.mianownik}
        D: ${wyższy.pojedyncza.męskorzeczowy.dopelniacz} | ${wyższy.mnoga.męskoosobowy.dopelniacz}
        C: ${wyższy.pojedyncza.męskorzeczowy.celownik} | ${wyższy.mnoga.męskoosobowy.celownik}
        B: ${wyższy.pojedyncza.męskorzeczowy.biernik} | ${wyższy.mnoga.męskoosobowy.biernik}
        N: ${wyższy.pojedyncza.męskorzeczowy.narzędnik} | ${wyższy.mnoga.męskoosobowy.narzędnik}
        M: ${wyższy.pojedyncza.męskorzeczowy.miejscownik} | ${wyższy.mnoga.męskoosobowy.miejscownik}
        ```
        
        """;

    private static final String COMPARATIVE_FEMININE = """
        *👩🏼Żeński* | *🙅🏼‍Niemęskoosobowy*
        ```
        M: ${wyższy.pojedyncza.żeński.mianownik} | ${wyższy.mnoga.niemęskoosobowy.mianownik}
        D: ${wyższy.pojedyncza.żeński.dopelniacz} | ${wyższy.mnoga.niemęskoosobowy.dopelniacz}
        C: ${wyższy.pojedyncza.żeński.celownik} | ${wyższy.mnoga.niemęskoosobowy.celownik}
        B: ${wyższy.pojedyncza.żeński.biernik} | ${wyższy.mnoga.niemęskoosobowy.biernik}
        N: ${wyższy.pojedyncza.żeński.narzędnik} | ${wyższy.mnoga.niemęskoosobowy.narzędnik}
        M: ${wyższy.pojedyncza.żeński.miejscownik} | ${wyższy.mnoga.niemęskoosobowy.miejscownik}
        ```
        
        """;

    private static final String COMPARATIVE_NEUTER = """
        *🍏Nijaki* | *🙅🏼‍Niemęskoosobowy*
        ```
        M: ${wyższy.pojedyncza.nijaki.mianownik} | ${wyższy.mnoga.niemęskoosobowy.mianownik}
        D: ${wyższy.pojedyncza.nijaki.dopelniacz} | ${wyższy.mnoga.niemęskoosobowy.dopelniacz}
        C: ${wyższy.pojedyncza.nijaki.celownik} | ${wyższy.mnoga.niemęskoosobowy.celownik}
        B: ${wyższy.pojedyncza.nijaki.biernik} | ${wyższy.mnoga.niemęskoosobowy.biernik}
        N: ${wyższy.pojedyncza.nijaki.narzędnik} | ${wyższy.mnoga.niemęskoosobowy.narzędnik}
        M: ${wyższy.pojedyncza.nijaki.miejscownik} | ${wyższy.mnoga.niemęskoosobowy.miejscownik}
        ```
        
        """;

    private final Adjective adjective;
    private final Map<String, String> placeholders = new HashMap<>();

    public String render() {
        String template = HEADER;

        placeholders.put("base", adjective.getBase());

        Map<String, String> masculine = adjective.get("pojedyncza.męskoosobowy");
        Map<String, String> masculineInanimate = adjective.get("pojedyncza.męskorzeczowy");
        Map<String, String> feminine = adjective.get("pojedyncza.żeński");
        Map<String, String> neuter = adjective.get("pojedyncza.nijaki");

        placeholders.putAll(adjective.get("mnoga.męskoosobowy"));
        placeholders.putAll(adjective.get("mnoga.niemęskoosobowy"));

        Map<String, String> comparativeMasculine = adjective.get("wyższy.pojedyncza.męskoosobowy");
        Map<String, String> comparativeMasculineInanimate = adjective.get("wyższy.pojedyncza.męskorzeczowy");
        Map<String, String> comparativeFeminine = adjective.get("wyższy.pojedyncza.żeński");
        Map<String, String> comparativeNeuter = adjective.get("wyższy.pojedyncza.nijaki");

        placeholders.putAll(adjective.get("wyższy.mnoga.męskoosobowy"));
        placeholders.putAll(adjective.get("wyższy.mnoga.niemęskoosobowy"));

        if (!masculine.isEmpty()) {
            template += MASCULINE;
            addForms(masculine);
        }

        if (!masculineInanimate.isEmpty()) {
            template += MASCULINE_INANIMATE;
            addForms(masculineInanimate);
        }

        if (!feminine.isEmpty()) {
            template += FEMININE;
            addForms(feminine);
        }

        if (!neuter.isEmpty()) {
            template += NEUTER;
            addForms(neuter);
        }

        if (!comparativeMasculine.isEmpty()) {
            template += COMPARATIVE_MASCULINE;
            addForms(comparativeMasculine);
        }

        if (!comparativeMasculineInanimate.isEmpty()) {
            template += COMPARATIVE_MASCULINE_INANIMATE;
            addForms(comparativeMasculineInanimate);
        }

        if (!comparativeFeminine.isEmpty()) {
            template += COMPARATIVE_FEMININE;
            addForms(comparativeFeminine);
        }

        if (!comparativeNeuter.isEmpty()) {
            template += COMPARATIVE_NEUTER;
            addForms(comparativeNeuter);
        }

        placeholders.replaceAll((k, v) -> v != null ? v : "-");

        return StringSubstitutor.replace(template, placeholders);
    }

    private void addForms(Map<String, String> forms) {
        int length = maxLength(forms.values().stream().toList());
        forms.forEach((key, value) -> placeholders.put(key, fixedString(value, length)));
    }
}
