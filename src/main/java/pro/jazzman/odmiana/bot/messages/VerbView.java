package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Verb;
import java.util.*;

@AllArgsConstructor
public class VerbView extends View {
    private static final String TEMPLATE = """
        `${infinitive}`
        
        *Część mowy*: czasownik
        """;

    private static final String PRESENT_TEMPLATE = """
        ⏰*Czas teraźniejszy* - *liczba pojedyncza* | *mnoga*
        ```
        1 os: ${pojedyncza.teraźniejszy.first} | ${mnoga.teraźniejszy.first}
        2 os: ${pojedyncza.teraźniejszy.second} | ${mnoga.teraźniejszy.second}
        3 os: ${pojedyncza.teraźniejszy.third} | ${mnoga.teraźniejszy.third}
        ```
        """;

    private static final String PAST_HEADER = "⏰*Czas przeszły* - *liczba pojedyncza* | *mnoga*";
    private static final String PAST_MASCULINE = """
        ```
        🧔🏼1 os: ${pojedyncza.przeszły.męski.first} | ${mnoga.przeszły.męski.first}
        🧔🏼‍2 os: ${pojedyncza.przeszły.męski.second} | ${mnoga.przeszły.męski.second}
        🧔🏼3 os: ${pojedyncza.przeszły.męski.third} | ${mnoga.przeszły.męski.third}
        ```
        """;

    private static final String PAST_FEMININE = """
        ```
        👩🏼1 os: ${pojedyncza.przeszły.żeński.first} | ${mnoga.przeszły.żeński.first}
        👩🏼2 os: ${pojedyncza.przeszły.żeński.second} | ${mnoga.przeszły.żeński.second}
        👩🏼3 os: ${pojedyncza.przeszły.żeński.third} | ${mnoga.przeszły.żeński.third}
        ```
        """;

    private static final String PAST_NEUTER = """
        ```
        🍏1 os: ${pojedyncza.przeszły.nijaki.first} | ${mnoga.przeszły.nijaki.first}
        🍏2 os: ${pojedyncza.przeszły.nijaki.second} | ${mnoga.przeszły.nijaki.second}
        🍏3 os: ${pojedyncza.przeszły.nijaki.third} | ${mnoga.przeszły.nijaki.third}
        ```
        """;

    private static final String FUTURE_SINGULAR_HEADER = "⏰*Czas przyszły* - *liczba pojedyncza*";

    private static final String FUTURE_SINGULAR_MASCULINE = """
        ```
        🧔🏼1 os: ${pojedyncza.przyszły.męski.first}
        🧔🏼‍2 os: ${pojedyncza.przyszły.męski.second}
        🧔🏼3 os: ${pojedyncza.przyszły.męski.third}
        ```
        """;

    private static final String FUTURE_SINGULAR_FEMININE = """
        ```
        👩🏼1 os: ${pojedyncza.przyszły.żeński.first}
        👩🏼2 os: ${pojedyncza.przyszły.żeński.second}
        👩🏼3 os: ${pojedyncza.przyszły.żeński.third}
        ```
        """;

    private static final String FUTURE_SINGULAR_NEUTER = """
        ```
        🍏1 os: ${pojedyncza.przyszły.nijaki.first}
        🍏2 os: ${pojedyncza.przyszły.nijaki.second}
        🍏3 os: ${pojedyncza.przyszły.nijaki.third}
        ```
        """;

    private static final String FUTURE_PLURAL_HEADER = "⏰*Czas przyszły* - *liczba mnoga*";

    private static final String FUTURE_PLURAL_MASCULINE = """
        ```
        🙎🏼‍1 os: ${mnoga.przyszły.męskoosobowy.first}
        🙎🏼‍2 os: ${mnoga.przyszły.męskoosobowy.second}
        🙎🏼‍3 os: ${mnoga.przyszły.męskoosobowy.third}
        ```
        """;

    private static final String FUTURE_PLURAL_NONMASCULINE = """
        ```
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
//        String template = TEMPLATE;
//
//        var placeholders = new HashMap<String, String>();
//        placeholders.put("infinitive", verb.getInfinitive());
//
//
//        if (verb.isPresent("pojedyncza.teraźniejszy") || verb.isPresent("mnoga.teraźniejszy")) {
//            template += PRESENT_TEMPLATE;
//
//            block("pojedyncza.teraźniejszy", placeholders);
//            block("mnoga.teraźniejszy", placeholders);
//        }
//
//        if (verb.isPresent("pojedyncza.przeszły") || verb.isPresent("mnoga.przeszły")) {
//            template += PAST_HEADER;
//
//            if (verb.isPresent("pojedyncza.przeszły.męski") || verb.isPresent("mnoga.przeszły.męski")) {
//                template += PAST_MASCULINE;
//
//                Map<String, String> singular = verb.get("pojedyncza.przeszły.męski");
//
//                singular
//                    .forEach(
//                        (key, value) -> placeholders.put(
//                            key, fixedString(value, maxLength(singular.values().stream().toList()))
//                        )
//                    );
//
//                Map<String, String> plural = verb.get("mnoga.przeszły.męski");
//
//                plural
//                    .forEach(
//                        (key, value) -> placeholders.put(
//                            key, fixedString(value, maxLength(plural.values().stream().toList()))
//                        )
//                    );
//            }
//        }
//
//
//
//        Map<String, String> pastSingular = verb.get("pojedyncza.przeszły");
//
//        if (!pastSingular.isEmpty()) {
//            template += PAST_TEMPLATE;
//            int length = maxLength(pastSingular.values().stream().toList());
//            pastSingular.forEach((key, value) -> placeholders.put(key, fixedString(value, length)));
//        }
//
//        placeholders.putAll(verb.get("mnoga.przeszły"));
//
//        Map<String, String> futureSingular = verb.get("pojedyncza.przyszły");
//
//        if (!futureSingular.isEmpty()) {
//            template += FUTURE_TEMPLATE;
//            placeholders.putAll(verb.get("pojedyncza.przyszły"));
//            placeholders.putAll(verb.get("mnoga.przyszły"));
//        }
//
//        Map<String, String> singularImperative = verb.get("pojedyncza.rozkazujący");
//        Map<String, String> pluralImperative = verb.get("mnoga.rozkazujący");
//
//        if (!singularImperative.isEmpty() && !pluralImperative.isEmpty()) {
//            template += IMPERATIVE_TEMPLATE;
//            int length = verb.getForms().get("pojedyncza.rozkazujący.second").length();
//
//            placeholders.put("pojedyncza.rozkazujący.placeholder", "-".repeat(length));
//            placeholders.put("pojedyncza.rozkazujący.second", verb.getForms().get("pojedyncza.rozkazujący.second"));
//            placeholders.putAll(pluralImperative);
//        }
//
//        if (verb.getForms().containsKey("bezosobnik")) {
//            template += "*Bezosobnik*: `" + verb.getForms().get("bezosobnik") + "`" + System.lineSeparator();
//        }
//
//        if (verb.getForms().containsKey("gerundium")) {
//            template += "*Gerundium*: `" + verb.getForms().get("gerundium") + "`" + System.lineSeparator();
//        }
//
//        if (verb.getForms().containsKey("imiesłów przysłówkowy współczesny")) {
//            template += "*Imiesłów przysłówkowy współczesny*: `" + verb.getForms().get("imiesłów przysłówkowy współczesny") + "`" + System.lineSeparator();
//        }
//
//        if (verb.getForms().containsKey("imiesłów przymiotnikowy czynny")) {
//            template += "*Imiesłów przymiotnikowy czynny*: `" + verb.getForms().get("imiesłów przymiotnikowy czynny") + "`" + System.lineSeparator();
//        }
//
//        if (verb.getForms().containsKey("imiesłów przymiotnikowy bierny")) {
//            template += "*Imiesłów przymiotnikowy bierny*: `" + verb.getForms().get("imiesłów przymiotnikowy bierny") + "`" + System.lineSeparator();
//        }
//
//        if (verb.getForms().containsKey("odpowiednik aspektowy")) {
//            template += "*Odpowiednik aspektowy*: `" + verb.getForms().get("odpowiednik aspektowy") + "`" + System.lineSeparator();
//        }
//
//        placeholders.replaceAll((k, v) -> v != null ? v : "-");

        return StringSubstitutor.replace(template(), verb.getForms());
    }

    private String template() {
        var placeholders = new HashMap<String, String>();

        String template = TEMPLATE;

        if (verb.isPresent("pojedyncza.teraźniejszy") || verb.isPresent("mnoga.teraźniejszy")) {
            template += PRESENT_TEMPLATE;

            Map<String, String> singular = verb.get("pojedyncza.teraźniejszy");

            singular
                .forEach(
                    (key, value) -> verb.put(
                        key, fixedString(value, maxLength(singular.values().stream().toList()))
                    )
                );
        }

        if (verb.isPresent("pojedyncza.przeszły") || verb.isPresent("mnoga.przeszły")) {
            template += PAST_HEADER;

            if (verb.isPresent("pojedyncza.przeszły.męski") || verb.isPresent("mnoga.przeszły.męski")) {
                template += PAST_MASCULINE;
            }

            if (verb.isPresent("pojedyncza.przeszły.żeński") || verb.isPresent("mnoga.przeszły.żeński")) {
                template += PAST_FEMININE;
            }

            if (verb.isPresent("pojedyncza.przeszły.nijaki") || verb.isPresent("mnoga.przeszły.nijaki")) {
                template += PAST_NEUTER;
            }
        }

        if (verb.isPresent("pojedyncza.przyszły")) {
            template += FUTURE_SINGULAR_HEADER;

            if (verb.isPresent("pojedyncza.przyszły.męski")) {
                template += FUTURE_SINGULAR_MASCULINE;
            }

            if (verb.isPresent("pojedyncza.przyszły.żeński")) {
                template += FUTURE_SINGULAR_FEMININE;
            }

            if (verb.isPresent("pojedyncza.przyszły.nijaki")) {
                template += FUTURE_SINGULAR_NEUTER;
            }
        }

        if (verb.isPresent("mnoga.przyszły")) {
            template += FUTURE_PLURAL_HEADER;

            if (verb.isPresent("mnoga.przyszły.męskoosobowy")) {
                template += FUTURE_PLURAL_MASCULINE;
            }

            if (verb.isPresent("mnoga.przyszły.niemęskoosobowy")) {
                template += FUTURE_PLURAL_NONMASCULINE;
            }
        }

        if (verb.isPresent("pojedyncza.rozkazujący") || verb.isPresent("mnoga.rozkazujący")) {
            template += IMPERATIVE_TEMPLATE;
        }

        if (verb.isPresent("bezosobnik")) {
            template += "*Bezosobnik*: `${bezosobnik}`\n";
        }

        if (verb.isPresent("gerundium")) {
            template += "*Gerundium*: `${gerundium}`\n";
        }

        if (verb.isPresent("imiesłów przysłówkowy współczesny")) {
            template += "*Imiesłów przysłówkowy współczesny*: `${imiesłów przysłówkowy współczesny}`\n";
        }

        if (verb.isPresent("imiesłów przymiotnikowy czynny")) {
            template += "*Imiesłów przymiotnikowy czynny*: `${imiesłów przymiotnikowy czynny}`\n";
        }

        if (verb.isPresent("imiesłów przymiotnikowy bierny")) {
            template += "*Imiesłów przymiotnikowy bierny*: `${imiesłów przymiotnikowy bierny}`\n";
        }

        if (verb.isPresent("odpowiednik aspektowy")) {
            template += "*Odpowiednik aspektowy*: `${odpowiednik aspektowy}`\n";
        }

        return template;
    }
}
