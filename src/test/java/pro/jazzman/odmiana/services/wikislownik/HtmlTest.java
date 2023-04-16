package pro.jazzman.odmiana.services.wikislownik;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
import pro.jazzman.odmiana.parsers.Parser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HtmlTest {
    private static final String LANG = "en";

    @Mock private Parser parser;

    @Test
    @DisplayName("Throws an exception if cannot find the word in HTML")
    void parseWordNotFoundThrowsException() throws Exception {
        when(parser.parse(any(Document.class))).thenReturn(null);

        assertThatThrownBy(
            () -> new Html("<html></html>", parser).parse(LANG)
        ).isExactlyInstanceOf(ApplicationRuntimeException.class);
    }
}