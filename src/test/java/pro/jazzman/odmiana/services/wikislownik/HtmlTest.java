package pro.jazzman.odmiana.services.wikislownik;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
import pro.jazzman.odmiana.parsers.Parser;

import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void translation() {
        String markup = """
            <ul>
                <li>ukraiński:
                    <span>(1.1)</span>
                    <a href="/">test</a>
                </li>
                <li>
                    angielski:
                    <span>(1.1)</span> <a href="/">foo</a> <link rel="" href=""><span><a href="/"><span><span>m</span></span></a></span> (qux);
                    <span>(1.2)</span> <a href="/">bar</a> <link rel="" href=""><span><a href="/"><span><span>ż</span></span></a></span> (quux),
                    <span>(1.3)</span> <a href="/">baz</a> <link rel="" href=""><span><a href="/"><span><span>n</span></span></a></span> (quuux);
                    <span>(1.4)</span> <a href="/">foo</a> <link rel="" href=""><span><a href="/"><span><span>n</span></span></a></span> (quuuux),
                    <span>(1.5)</span> <a href="/">navy blue</a> <link rel="" href=""><span><a href="/"><span><span>n</span></span></a></span> (blue)
                </li>
            </ul>
            """;

        assertThat(new Html(markup, parser).translation("en")).isEqualTo("foo, bar, baz, navy blue");
    }
}