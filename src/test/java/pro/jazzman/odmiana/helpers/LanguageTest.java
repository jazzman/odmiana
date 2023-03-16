package pro.jazzman.odmiana.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Language")
class LanguageTest {

    @Test
    @DisplayName("Resolves language by code")
    void byCode() {
        assertThat(Language.byCode("uk")).isEqualTo("ukraiński");
    }

    @Test
    @DisplayName("Returns fallback language")
    void byCodeNotExistReturnFallback() {
        assertThat(Language.byCode("gg")).isEqualTo("angielski");
    }
}