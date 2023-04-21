package pro.jazzman.odmiana.bot.replies;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.Times;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.services.HistoryService;
import pro.jazzman.odmiana.services.vocabulary.WSJP;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockito.Mockito.verify;

/**
 * It does not make sense to test implementation in this case. Hopefully integration testing of the whole flow will make future refactoring easier
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("[Integration] Default reply")
@Testcontainers
@Slf4j
class DefaultReplyTest {
    private static final int MOCK_SERVER_PORT = 1080;
    private static final String VERB = "kocham";
    private static final String BAD_WORD = "ddddddd";
    private static final String ADJECTIVE = "granatowego";
    private static final String ADJECTIVE_MIANOWNIK = "granatowy";
    private static final String LANGUAGE = "en";

    private DefaultReply reply;
    private Update update;
    private final Message message = new Message();
    private final User user = new User();

    @Mock HistoryService historyService;

    public static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
        .parse("mockserver/mockserver")
        .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());

    @Container
    public final MockServerContainer mockServer = new MockServerContainer(MOCKSERVER_IMAGE).withExposedPorts(MOCK_SERVER_PORT);

    @BeforeEach
    void init() {
        reply = new DefaultReply(
            new WSJP(String.format("http://%s:%d", mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))),
            historyService
        );

        user.setId(1L);
        user.setLanguageCode(LANGUAGE);

        message.setFrom(user);

        update = new Update();
        update.setMessage(message);
    }

    private String readFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(name);

        if (url == null) {
            throw new FileNotFoundException("Unable to find '" + name + "' file");
        }

        return FileUtils.readFileToString(new File(url.getFile()), StandardCharsets.UTF_8);
    }


    @Test
    @DisplayName("[Verb] Parses responses from the source and sends an expected message")
    void onMessageVerbWSJP(@Mock OdmianaBot bot) {
        final String verb = "kocham";

        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/szukaj/podstawowe/wyniki").withQueryStringParameter("szukaj", verb), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/verb/search-results.html")));

            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/haslo/podglad/37947"), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/verb/meanings.html")));

            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/haslo/podglad/37947/kochac/4060071/matke"), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/verb/final.html")));

            message.setText(verb);
            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/verb.success.txt"), update);
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Test
    @DisplayName("Sends an error if the word is not found on the source")
    void onMessageWordNotFoundWSJP(@Mock OdmianaBot bot) {
        final String word = "invalidword";

        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/szukaj/podstawowe/wyniki").withQueryStringParameter("szukaj", word), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/not-found.html")));

            message.setText(word);
            update.setMessage(message);

            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/not-found.txt"), update);
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Test
    @DisplayName("[Noun] Parses responses from the source and sends an expected message")
    void onMessageNounWSJP(@Mock OdmianaBot bot) {
        final String noun = "kobiecie";

        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/szukaj/podstawowe/wyniki").withQueryStringParameter("szukaj", noun), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/noun/search-results.html")));

            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/haslo/podglad/3950"), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/noun/meanings.html")));

            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("https://wsjp.pl/haslo/podglad/3950/kobieta/4891319"), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/noun/final.html")));

            message.setText(noun);
            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/noun.success.txt"), update);
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }

    @Test
    @DisplayName("[Noun] Parses responses from the source and sends an expected message")
    void onMessagePluralNounWSJP(@Mock OdmianaBot bot) {
        final String noun = "drzwiom";

        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/szukaj/podstawowe/wyniki").withQueryStringParameter("szukaj", noun), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/noun/plural.search-results.html")));

            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/haslo/podglad/4088"), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/noun/plural.meanings.html")));

            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("https://wsjp.pl/haslo/podglad/4088/drzwi/2848197"), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wsjp/noun/plural.final.html")));

            message.setText(noun);
            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/noun.plural.success.txt"), update);
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
    }
}