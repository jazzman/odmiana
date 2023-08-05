package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import pro.jazzman.odmiana.bot.messages.AdjectiveView;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Adjective implements Word {
    @Nullable
    private String base;
    private Map<String, String> forms = new HashMap<>();

    @Override
    public String message() {
        return new AdjectiveView(this).render();
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
