package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;
import java.util.*;

@AllArgsConstructor
public class VerbView extends View {
    private static final String TEMPLATE = """
        `${infinitive}`
        
        *Część mowy*: czasownik
            
        ⏰*Czas teraźniejszy* - *liczba pojedyncza* | *mnoga*
        ```
        1 os: ${pojedyncza.teraźniejszy.first} | ${mnoga.teraźniejszy.first}
        2 os: ${pojedyncza.teraźniejszy.second} | ${mnoga.teraźniejszy.second}
        3 os: ${pojedyncza.teraźniejszy.third} | ${mnoga.teraźniejszy.third}
        ```
        
        ⏰*Czas przeszły* - *liczba pojedyncza* | *mnoga*
        ```
        🧔🏼1 os: ${pojedyncza.przeszły.męski.first} | ${mnoga.przeszły.męski.first}
        🧔🏼‍2 os: ${pojedyncza.przeszły.męski.second} | ${mnoga.przeszły.męski.second}
        🧔🏼3 os: ${pojedyncza.przeszły.męski.third} | ${mnoga.przeszły.męski.third}
            
        👩🏼1 os: ${pojedyncza.przeszły.żeński.first} | ${mnoga.przeszły.żeński.first}
        👩🏼2 os: ${pojedyncza.przeszły.żeński.second} | ${mnoga.przeszły.żeński.second}
        👩🏼3 os: ${pojedyncza.przeszły.żeński.third} | ${mnoga.przeszły.żeński.third}
            
        🍏1 os: ${pojedyncza.przeszły.nijaki.first} | ${mnoga.przeszły.nijaki.first}
        🍏2 os: ${pojedyncza.przeszły.nijaki.second} | ${mnoga.przeszły.nijaki.second}
        🍏3 os: ${pojedyncza.przeszły.nijaki.third} | ${mnoga.przeszły.nijaki.third}
        ```
        
        """;

    private static final String FUTURE_TEMPLATE = """
        ⏰*Czas przyszły* - *liczba pojedyncza*
        ```
        🧔🏼1 os: ${pojedyncza.przyszły.męski.first}
        🧔🏼‍2 os: ${pojedyncza.przyszły.męski.second}
        🧔🏼3 os: ${pojedyncza.przyszły.męski.third}
        
        👩🏼1 os: ${pojedyncza.przyszły.żeński.first}
        👩🏼2 os: ${pojedyncza.przyszły.żeński.second}
        👩🏼3 os: ${pojedyncza.przyszły.żeński.third}
        
        🍏1 os: ${pojedyncza.przyszły.nijaki.first}
        🍏2 os: ${pojedyncza.przyszły.nijaki.second}
        🍏3 os: ${pojedyncza.przyszły.nijaki.third}
        ```
        
        ⏰*Czas przyszły* - *liczba mnoga*
        ```
        🙎🏼‍1 os: ${mnoga.przyszły.męskoosobowy.first}
        🙎🏼‍2 os: ${mnoga.przyszły.męskoosobowy.second}
        🙎🏼‍3 os: ${mnoga.przyszły.męskoosobowy.third}
        
        🙅🏼‍1 os: ${mnoga.przyszły.niemęskoosobowy.first}
        🙅🏼‍2 os: ${mnoga.przyszły.niemęskoosobowy.second}
        🙅🏼‍3 os: ${mnoga.przyszły.niemęskoosobowy.third}
        ```
        
        """;

    private static final String IMPERATIVE_TEMPLATE = """
        📢*Tryb rozkazujący*
        ```
        1 os: ${pojedyncza.rozkazujący.placeholder} | ${mnoga.rozkazujący.first}
        2 os: ${pojedyncza.rozkazujący.second} | ${mnoga.rozkazujący.second}
        ```
        
        """;

    private Verb verb;

    public String render() {
        String template = TEMPLATE;

        var placeholders = new HashMap<String, String>();
        placeholders.put("infinitive", verb.getInfinitive());

        Map<String, String> presentSingular = verb.get("pojedyncza.teraźniejszy");

        if (!presentSingular.isEmpty()) {
            int length = maxLength(presentSingular.values().stream().toList());
            presentSingular.forEach((key, value) -> placeholders.put(key, fixedString(value, length)));
        }

        placeholders.putAll(verb.get("mnoga.teraźniejszy"));

        Map<String, String> pastSingular = verb.get("pojedyncza.przeszły");

        if (!pastSingular.isEmpty()) {
            int length = maxLength(pastSingular.values().stream().toList());
            pastSingular.forEach((key, value) -> placeholders.put(key, fixedString(value, length)));
        }

        placeholders.putAll(verb.get("mnoga.przeszły"));

        Map<String, String> futureSingular = verb.get("pojedyncza.przyszły");

        if (!futureSingular.isEmpty()) {
            template += FUTURE_TEMPLATE;
            placeholders.putAll(verb.get("pojedyncza.przyszły"));
            placeholders.putAll(verb.get("mnoga.przyszły"));
        }

        Map<String, String> singularImperative = verb.get("pojedyncza.rozkazujący");
        Map<String, String> pluralImperative = verb.get("mnoga.rozkazujący");

        if (!singularImperative.isEmpty() && !pluralImperative.isEmpty()) {
            template += IMPERATIVE_TEMPLATE;
            int length = verb.getForms().get("pojedyncza.rozkazujący.second").length();

            placeholders.put("pojedyncza.rozkazujący.placeholder", "-".repeat(length));
            placeholders.put("pojedyncza.rozkazujący.second", verb.getForms().get("pojedyncza.rozkazujący.second"));
            placeholders.putAll(pluralImperative);
        }

        if (verb.getForms().containsKey("bezosobnik")) {
            template += "*Bezosobnik*: `" + verb.getForms().get("bezosobnik") + "`" + System.lineSeparator();
        }

        if (verb.getForms().containsKey("gerundium")) {
            template += "*Gerundium*: `" + verb.getForms().get("gerundium") + "`" + System.lineSeparator();
        }

        if (verb.getForms().containsKey("imiesłów przysłówkowy współczesny")) {
            template += "*Imiesłów przysłówkowy współczesny*: `" + verb.getForms().get("imiesłów przysłówkowy współczesny") + "`" + System.lineSeparator();
        }

        if (verb.getForms().containsKey("imiesłów przymiotnikowy czynny")) {
            template += "*Imiesłów przymiotnikowy czynny*: `" + verb.getForms().get("imiesłów przymiotnikowy czynny") + "`" + System.lineSeparator();
        }

        if (verb.getForms().containsKey("imiesłów przymiotnikowy bierny")) {
            template += "*Imiesłów przymiotnikowy bierny*: `" + verb.getForms().get("imiesłów przymiotnikowy bierny") + "`" + System.lineSeparator();
        }

        if (verb.getForms().containsKey("odpowiednik aspektowy")) {
            template += "*Odpowiednik aspektowy*: `" + verb.getForms().get("odpowiednik aspektowy") + "`" + System.lineSeparator();
        }

        placeholders.replaceAll((k, v) -> v != null ? v : "-");

        return StringSubstitutor.replace(template, placeholders);
    }
}
