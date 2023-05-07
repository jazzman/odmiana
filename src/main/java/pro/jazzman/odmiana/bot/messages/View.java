package pro.jazzman.odmiana.bot.messages;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

abstract public class View {
    private final static int DEFAULT_LENGTH = 5;

    abstract public String render();

    public int maxLength(String... words) {
        if (words == null) {
            return DEFAULT_LENGTH;
        }

        return Arrays.stream(words)
            .filter(Objects::nonNull)
            .filter(e -> !e.isEmpty())
            .max(Comparator.comparing(String::length))
            .map(String::length)
            .orElse(DEFAULT_LENGTH);
    }

    public int maxLength(List<String> words) {
        if (words == null) {
            return DEFAULT_LENGTH;
        }

        return maxLength(
            words.toArray(String[]::new)
        );
    }

    public String fixedString(String word, int length) {
        if (word != null && !word.isBlank()) {
            return StringUtils.rightPad(word, length);
        }

        return "-".repeat(length);
    }
}
