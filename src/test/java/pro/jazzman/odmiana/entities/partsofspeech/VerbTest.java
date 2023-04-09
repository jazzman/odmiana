package pro.jazzman.odmiana.entities.partsofspeech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class VerbTest {
    private final Verb verb = new Verb();

    @Test
    @DisplayName("Has a translation if set")
    void hasTranslation() {
        verb.setTranslation("love");

        assertThat(verb.hasTranslation()).isTrue();
    }

    @Test
    @DisplayName("Does not have a translation if not set")
    void hasTranslationNoTranslationReturnsFalse() {
        assertThat(verb.hasTranslation()).isFalse();
    }
}