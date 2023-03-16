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
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.services.HistoryService;
import pro.jazzman.odmiana.services.vocabulary.SJP;
import pro.jazzman.odmiana.services.vocabulary.Wikislownik;

import static org.mockito.ArgumentMatchers.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * It does not make sense to test implementation in this case. Hopefully integration testing of the whole flow will make future refactoring easier
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("[Integration] Default reply")
@Testcontainers
@Slf4j
class DefaultReplyTest {
    private static final int MOCK_SERVER_PORT = 1080;
    private static final String WORD = "kocham";
    private static final String BAD_WORD = "ddddddd";
    private static final String INFINITIVE = "kochać";
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
            new Wikislownik(String.format("http://%s:%d/page=", mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))),
            new SJP(String.format("http://%s:%d", mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))),
            historyService
        );

        user.setId(1L);
        user.setLanguageCode(LANGUAGE);

        message.setFrom(user);
        message.setText(WORD);

        update = new Update();
        update.setMessage(message);
    }

    @Test
    @DisplayName("Parsed responses from 3rd-parties and sends the correct result")
    void onMessage(@Mock OdmianaBot bot) {
        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client // sjp
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/" + URLEncoder.encode(WORD, StandardCharsets.UTF_8)), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("sjp/responses/200.html")));

            client // wikislownik
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/page=" + URLEncoder.encode(INFINITIVE, StandardCharsets.UTF_8)), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody(readFile("wikislownik/responses/200.html")));

            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/success.txt"), update);
        } catch (Exception e) {
            throw new RuntimeException("Unable to run integration test with mocks", e);
        }
    }

    @Test
    @DisplayName("Sends an error if the word is not found")
    void onMessageNotFoundSendError(@Mock OdmianaBot bot) {
        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/" + URLEncoder.encode(BAD_WORD, StandardCharsets.UTF_8)), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.NOT_FOUND.value()).withBody(readFile("sjp/responses/404.html")));

            message.setText(BAD_WORD);
            update.setMessage(message);

            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/not-found.txt"), update);
        } catch (Exception e) {
            throw new RuntimeException("Unable to run integration test with mocks", e);
        }
    }

    @Test
    @DisplayName("Sends an error if SJP returned 200 but no word HTML")
    void onMessageNotFoundInHTMLSendError(@Mock OdmianaBot bot) {
        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/" + URLEncoder.encode(WORD, StandardCharsets.UTF_8)), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.OK.value()).withBody("<html></html>"));

            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/not-found.txt"), update);
        } catch (Exception e) {
            throw new RuntimeException("Unable to run integration test with mocks", e);
        }
    }

    @Test
    @DisplayName("Sends an error if an error occurred while parsed SJP")
    void onMessageErrorOccurredWhileRequestingSJPSendError(@Mock OdmianaBot bot) {
        try (
            MockServerClient client = new MockServerClient(mockServer.getHost(), mockServer.getMappedPort(MOCK_SERVER_PORT))
        ) {
            client
                .when(request().withMethod(HttpMethod.GET.name()).withPath("/" + URLEncoder.encode(WORD, StandardCharsets.UTF_8)), Times.exactly(1))
                .respond(response().withStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value()).withBody("<html></html>"));

            reply.onMessage(bot, update);

            verify(bot).send(readFile("telegram/responses/error.txt"), update);
        } catch (Exception e) {
            throw new RuntimeException("Unable to run integration test with mocks", e);
        }
    }

    @Test
    @DisplayName("Sends an error if exception was thrown")
    void onMessageExceptionSendError(@Mock OdmianaBot bot, @Mock Wikislownik wikislownik, @Mock SJP sjp) throws Exception {
        when(sjp.get(anyString())).thenThrow(Exception.class);
        new DefaultReply(wikislownik, sjp, historyService).onMessage(bot, update);

        verify(bot).send(readFile("telegram/responses/error.txt"), update);
    }

    private String readFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(name);

        if (url == null) {
            throw new FileNotFoundException("Unable to find '" + name + "' file");
        }

        return FileUtils.readFileToString(new File(url.getFile()), StandardCharsets.UTF_8);
    }
}