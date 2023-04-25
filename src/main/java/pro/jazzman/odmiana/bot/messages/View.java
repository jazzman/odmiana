package pro.jazzman.odmiana.bot.messages;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

abstract public class View {
    abstract public String render();

    public int maxLength(String... words) {
        return Arrays.stream(words)
            .filter(Objects::nonNull)
            .max(Comparator.comparing(String::length))
            .map(String::length).orElse(5);
    }

    public int maxLength(List<String> words) {
        return maxLength(words.toArray(new String[0]));
    }

    public String fixedString(String word, int length) {
        if (word != null && !word.isBlank()) {
            return StringUtils.rightPad(word, length);
        }

        return "-".repeat(length);
    }
}
