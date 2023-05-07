package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import pro.jazzman.odmiana.bot.messages.VerbView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Verb implements Word {
    private String infinitive;

    private Map<String, String> forms = new HashMap<>();

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
}














