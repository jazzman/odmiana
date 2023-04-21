package pro.jazzman.odmiana.entities.partsofspeech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class NounTest {
    private final Noun noun = new Noun();

    @Test
    @DisplayName("Has a translation if set")
    void hasTranslation() {
        noun.setTranslation("love");

        assertThat(noun.hasTranslation()).isTrue();
    }

    @Test
    @DisplayName("Does not have a translation if not set")
    void hasTranslationNoTranslationReturnsFalse() {
        noun.setTranslation(null);

        assertThat(noun.hasTranslation()).isFalse();
    }
}