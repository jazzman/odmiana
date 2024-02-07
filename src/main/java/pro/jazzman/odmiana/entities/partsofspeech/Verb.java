package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pro.jazzman.odmiana.bot.messages.VerbView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Verb implements Word {
    @Nullable
    private String infinitive;

    private Map<String, String> forms = new HashMap<>();

    public Verb() {
        forms.put("pojedyncza.teraźniejszy.first",          "");
        forms.put("pojedyncza.teraźniejszy.second",         "");
        forms.put("pojedyncza.teraźniejszy.third",          "");

        forms.put("mnoga.teraźniejszy.first",               "");
        forms.put("mnoga.teraźniejszy.second",              "");
        forms.put("mnoga.teraźniejszy.third",               "");

        forms.put("pojedyncza.przeszły.męski.first",        "");
        forms.put("pojedyncza.przeszły.męski.second",       "");
        forms.put("pojedyncza.przeszły.męski.third",        "");

        forms.put("pojedyncza.przeszły.żeński.first",       "");
        forms.put("pojedyncza.przeszły.żeński.second",      "");
        forms.put("pojedyncza.przeszły.żeński.third",       "");

        forms.put("pojedyncza.przeszły.nijaki.first",       "");
        forms.put("pojedyncza.przeszły.nijaki.second",      "");
        forms.put("pojedyncza.przeszły.nijaki.third",       "");

        forms.put("mnoga.przeszły.męski.first",             "");
        forms.put("mnoga.przeszły.męski.second",            "");
        forms.put("mnoga.przeszły.męski.third",             "");

        forms.put("mnoga.przeszły.żeński.first",            "");
        forms.put("mnoga.przeszły.żeński.second",           "");
        forms.put("mnoga.przeszły.żeński.third",            "");

        forms.put("mnoga.przeszły.nijaki.first",            "");
        forms.put("mnoga.przeszły.nijaki.second",           "");
        forms.put("mnoga.przeszły.nijaki.third",            "");

        forms.put("pojedyncza.przyszły.męski.first",        "");
        forms.put("pojedyncza.przyszły.męski.second",       "");
        forms.put("pojedyncza.przyszły.męski.third",        "");

        forms.put("pojedyncza.przyszły.żeński.first",       "");
        forms.put("pojedyncza.przyszły.żeński.second",      "");
        forms.put("pojedyncza.przyszły.żeński.third",       "");

        forms.put("pojedyncza.przyszły.nijaki.first",       "");
        forms.put("pojedyncza.przyszły.nijaki.second",      "");
        forms.put("pojedyncza.przyszły.nijaki.third",       "");

        forms.put("mnoga.przyszły.męskoosobowy.first",      "");
        forms.put("mnoga.przyszły.męskoosobowy.second",     "");
        forms.put("mnoga.przyszły.męskoosobowy.third",      "");

        forms.put("mnoga.przyszły.niemęskoosobowy.first",   "");
        forms.put("mnoga.przyszły.niemęskoosobowy.second",  "");
        forms.put("mnoga.przyszły.niemęskoosobowy.third",   "");

        forms.put("pojedyncza.rozkazujący.second",          "");
        forms.put("mnoga.rozkazujący.first",                "");
        forms.put("mnoga.rozkazujący.second",               "");

        forms.put("bezosobnik",                             "");
        forms.put("gerundium",                              "");
        forms.put("imiesłów przysłówkowy współczesny",      "");
        forms.put("imiesłów przymiotnikowy czynny",         "");
        forms.put("imiesłów przymiotnikowy bierny",         "");
        forms.put("odpowiednik aspektowy",                  "");
    }

    @Override
    public String message() {
        return new VerbView(this).render();
    }

    public void put(String key, String value) {
        forms.put(key, value);
    }

    public Map<String, String> get(String pattern) {
        return forms.entrySet()
            .stream()
            .filter(e -> e.getKey().startsWith(pattern))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean isAbsent(String pattern) {
        return forms.entrySet()
            .stream()
            .filter(e -> e.getKey().startsWith(pattern) && e.getValue() != null && !e.getValue().trim().isEmpty())
            .findAny()
            .isEmpty();
    }

    public boolean isPresent(String pattern) {
        return !isAbsent(pattern);
    }
}














