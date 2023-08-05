package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pro.jazzman.odmiana.bot.messages.NounView;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Noun implements Word {
    @Nullable
    private String base;

    @Nullable
    private NounType type;
    private Map<String, String> forms = new HashMap<>();

    public Noun() {
        forms.put("pojedyncza.mianownik",    null);
        forms.put("pojedyncza.dopelniacz",   null);
        forms.put("pojedyncza.celownik",     null);
        forms.put("pojedyncza.biernik",      null);
        forms.put("pojedyncza.narzędnik",    null);
        forms.put("pojedyncza.miejscownik",  null);
        forms.put("pojedyncza.wolacz",       null);

        forms.put("mnoga.mianownik",    null);
        forms.put("mnoga.dopelniacz",   null);
        forms.put("mnoga.celownik",     null);
        forms.put("mnoga.biernik",      null);
        forms.put("mnoga.narzędnik",    null);
        forms.put("mnoga.miejscownik",  null);
        forms.put("mnoga.wolacz",       null);
    }

    @Override
    public String message() {
        return new NounView(this).render();
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
}
