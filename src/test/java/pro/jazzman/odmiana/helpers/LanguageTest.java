package pro.jazzman.odmiana.helpers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Language")
class LanguageTest {
    @ParameterizedTest
    @MethodSource("langsMapping")
    @DisplayName("Resolves Ukrainian language by code")
    void byCode(String code, String languageInPolish) {
        assertThat(Language.byCode(code)).isEqualTo(languageInPolish);
    }

    @Test
    @DisplayName("Returns fallback language")
    void byCodeNotExistReturnFallback() {
        assertThat(Language.byCode("gg")).isEqualTo("angielski");
    }

    private static Stream<Arguments> langsMapping() {
        return Stream.of(
            Arguments.of("uk", "ukraiński"),
            Arguments.of("it", "włoski"),
            Arguments.of("de", "niemiecki"),
            Arguments.of("es", "hiszpański"),
            Arguments.of("cs", "czeski"),
            Arguments.of("da", "duński"),
            Arguments.of("nl", "holenderski"),
            Arguments.of("ja", "japoński"),
            Arguments.of("en", "angielski")
        );
    }
}