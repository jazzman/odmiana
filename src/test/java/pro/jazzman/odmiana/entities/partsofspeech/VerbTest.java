package pro.jazzman.odmiana.entities.partsofspeech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

class VerbTest {
    private final Verb verb = new Verb();

    @BeforeEach
    void setUp() {
        verb.setInfinitive("kochać");
        verb.setSingularPresent1("kocham");
        verb.setSingularPresent2("kochasz");
        verb.setSingularPresent3("kocha");
        verb.setSingularPastMale1("kochałem");
        verb.setSingularPastMale2("kochałeś");
        verb.setSingularPastMale3("kochał");
        verb.setSingularPastFemale1("kochałam");
        verb.setSingularPastFemale2("kochałaś");
        verb.setSingularPastFemale3("kochała");
        verb.setSingularPastNeutral1("kochałom");
        verb.setSingularPastNeutral2("kochałoś");
        verb.setSingularPastNeutral3("kochało");
        verb.setSingularImperative1("niech kocham");
        verb.setSingularImperative2("kochaj");
        verb.setSingularImperative3("niech kocha");

        verb.setPluralPresent1("kochamy");
        verb.setPluralPresent2("kochacie");
        verb.setPluralPresent3("kochają");
        verb.setPluralPastMale1("kochaliśmy");
        verb.setPluralPastMale2("kochaliście");
        verb.setPluralPastMale3("kochali");
        verb.setPluralPastFemale1("kochałyśmy");
        verb.setPluralPastFemale2("kochałyście");
        verb.setPluralPastFemale3("kochały");
        verb.setPluralPastNeutral1("kochałyśmy");
        verb.setPluralPastNeutral2("kochałyście");
        verb.setPluralPastNeutral3("kochały");
        verb.setPluralImperative1("kochajmy");
        verb.setPluralImperative2("kochajcie");
        verb.setPluralImperative3("niech kochają");
    }

    @Test
    @DisplayName("Verb getters (to make Sonar happy)")
    void getters() {
        assertThat(verb.getInfinitive()).isEqualTo("kochać");
        assertThat(verb.getSingularPresent1()).isEqualTo("kocham");
        assertThat(verb.getSingularPresent2()).isEqualTo("kochasz");
        assertThat(verb.getSingularPresent3()).isEqualTo("kocha");
        assertThat(verb.getSingularPastMale1()).isEqualTo("kochałem");
        assertThat(verb.getSingularPastMale2()).isEqualTo("kochałeś");
        assertThat(verb.getSingularPastMale3()).isEqualTo("kochał");
        assertThat(verb.getSingularPastFemale1()).isEqualTo("kochałam");
        assertThat(verb.getSingularPastFemale2()).isEqualTo("kochałaś");
        assertThat(verb.getSingularPastFemale3()).isEqualTo("kochała");
        assertThat(verb.getSingularPastNeutral1()).isEqualTo("kochałom");
        assertThat(verb.getSingularPastNeutral2()).isEqualTo("kochałoś");
        assertThat(verb.getSingularPastNeutral3()).isEqualTo("kochało");
        assertThat(verb.getSingularImperative1()).isEqualTo("niech kocham");
        assertThat(verb.getSingularImperative2()).isEqualTo("kochaj");
        assertThat(verb.getSingularImperative3()).isEqualTo("niech kocha");

        assertThat(verb.getPluralPresent1()).isEqualTo("kochamy");
        assertThat(verb.getPluralPresent2()).isEqualTo("kochacie");
        assertThat(verb.getPluralPresent3()).isEqualTo("kochają");
        assertThat(verb.getPluralPastMale1()).isEqualTo("kochaliśmy");
        assertThat(verb.getPluralPastMale2()).isEqualTo("kochaliście");
        assertThat(verb.getPluralPastMale3()).isEqualTo("kochali");
        assertThat(verb.getPluralPastFemale1()).isEqualTo("kochałyśmy");
        assertThat(verb.getPluralPastFemale2()).isEqualTo("kochałyście");
        assertThat(verb.getPluralPastFemale3()).isEqualTo("kochały");
        assertThat(verb.getPluralPastNeutral1()).isEqualTo("kochałyśmy");
        assertThat(verb.getPluralPastNeutral2()).isEqualTo("kochałyście");
        assertThat(verb.getPluralPastNeutral3()).isEqualTo("kochały");
        assertThat(verb.getPluralImperative1()).isEqualTo("kochajmy");
        assertThat(verb.getPluralImperative2()).isEqualTo("kochajcie");
        assertThat(verb.getPluralImperative3()).isEqualTo("niech kochają");
    }

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