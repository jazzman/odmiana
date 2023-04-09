package pro.jazzman.odmiana.entities.partsofspeech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

class NounTest {
    private final Noun noun = new Noun();

    @BeforeEach
    void setUp() {
        noun.setMianownikSingular("miłość");
        noun.setDopelniaczSingular("miłości");
        noun.setCelownikSingular("miłości");
        noun.setBiernikSingular("miłość");
        noun.setNarzednikSingular("miłością");
        noun.setMiejscownikSingular("miłości");
        noun.setWolaczSingular("miłości");
        noun.setMianownikPlural("miłości");
        noun.setDopelniaczPlural("miłości");
        noun.setCelownikPlural("miłościom");
        noun.setBiernikPlural("miłości");
        noun.setNarzednikPlural("miłościami");
        noun.setMiejscownikPlural("miłościach");
        noun.setWolaczPlural("miłości");
    }


    @Test
    @DisplayName("Noun getters (to make Sonar happy)")
    void getters() {
        assertThat(noun.getMianownikSingular()).isEqualTo("miłość");
        assertThat(noun.getDopelniaczSingular()).isEqualTo("miłości");
        assertThat(noun.getCelownikSingular()).isEqualTo("miłości");
        assertThat(noun.getBiernikSingular()).isEqualTo("miłość");
        assertThat(noun.getNarzednikSingular()).isEqualTo("miłością");
        assertThat(noun.getMiejscownikSingular()).isEqualTo("miłości");
        assertThat(noun.getWolaczSingular()).isEqualTo("miłości");
        assertThat(noun.getMianownikPlural()).isEqualTo("miłości");
        assertThat(noun.getDopelniaczPlural()).isEqualTo("miłości");
        assertThat(noun.getCelownikPlural()).isEqualTo("miłościom");
        assertThat(noun.getBiernikPlural()).isEqualTo("miłości");
        assertThat(noun.getNarzednikPlural()).isEqualTo("miłościami");
        assertThat(noun.getMiejscownikPlural()).isEqualTo("miłościach");
        assertThat(noun.getWolaczPlural()).isEqualTo("miłości");
    }

    @Test
    @DisplayName("Has singular a form when has a singular mianownik")
    void hasSingular() {
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