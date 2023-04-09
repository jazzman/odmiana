package pro.jazzman.odmiana.entities.partsofspeech;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class NounTest {
    private final Noun noun = new Noun();

    @Test
    @DisplayName("Has singular a form when has a singular mianownik")
    void hasSingular() {
        noun.setMianownikSingular("miłość");

        assertThat(noun.hasSingular()).isTrue();
    }

    @Test
    @DisplayName("Does not have a singular form when does not have a singular mianownik")
    void hasSingularNoMianownikSingularReturnsFalse() {
        noun.setMianownikSingular(null);

        assertThat(noun.hasSingular()).isFalse();
    }

    @Test
    @DisplayName("Has a plural form when has a plural mianownik")
    void hasPlural() {
        noun.setMianownikPlural("miłości");

        assertThat(noun.hasPlural()).isTrue();
    }

    @Test
    @DisplayName("Does not have a singular form when does not have a singular mianownik")
    void hasPluralNoMianownikPluralReturnsFalse() {
        noun.setMianownikPlural(null);

        assertThat(noun.hasPlural()).isFalse();
    }

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