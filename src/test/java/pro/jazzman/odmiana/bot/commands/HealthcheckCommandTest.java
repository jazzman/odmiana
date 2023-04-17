package pro.jazzman.odmiana.bot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.interfaces.Privacy;
import pro.jazzman.odmiana.bot.messages.HealthcheckView;
import pro.jazzman.odmiana.configurations.Config;
import pro.jazzman.odmiana.services.ApplicationHealthIndicator;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Healthcheck command")
class HealthcheckCommandTest {
    private static final Long OWNER_ID = 1L;
    private static final String MESSAGE_TEXT = """
        Version: *1.2.3*
        Application: *UP*
        Database: *UP*
        Disk: *UP*
        Total Disk Space: *1000000000*
        Free Disk Space: *999999999*
        """;

    @Mock private OdmianaBot bot;
    @Mock private Config config;
    @Mock private HealthcheckView view;
    @Mock private ApplicationHealthIndicator healthIndicator;
    private HealthcheckCommand command;

    @BeforeEach
    void setUp() {
        command = new HealthcheckCommand();

        ReflectionTestUtils.setField(command, "config", config);
        ReflectionTestUtils.setField(command, "view", view);
        ReflectionTestUtils.setField(command, "healthIndicator", healthIndicator);
    }

    @Test
    @DisplayName("Has a correct command")
    void getCommand() {
        assertThat(command.getCommand()).isEqualTo("/healthcheck");
    }

    @Test
    @DisplayName("Has a usage example")
    void getUsage() {
        assertThat(command.getUsage()).isEqualTo("/healthcheck");
    }

    @Test
    @DisplayName("Has a description")
    void getDescription() {
        assertThat(command.getDescription()).isEqualTo("Application Healthcheck");
    }

    @Test
    @DisplayName("Has OWNER privacy level")
    void privacy() {
        assertThat(command.privacy()).isEqualTo(Privacy.OWNER);
    }

    @Test
    @DisplayName("Send message on the compatible privacy level")
    void handle() throws TelegramApiException {
        Chat chat = new Chat();
        chat.setId(OWNER_ID);
        Message message = new Message();
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);

        Health.Builder status = Health.up();
        status.withDetails(Map.of(
            "version", "1.2.3",
            "ping.status", "UP",
            "database.status", "UP",
            "disk.status", "UP",
            "disk.total", "1000000000",
            "disk.free", "987654321"
        ));

        when(healthIndicator.health()).thenReturn(status.build());

        when(config.getOwnerId()).thenReturn(OWNER_ID);
        when(view.render(any(Health.class))).thenReturn(MESSAGE_TEXT);

        command.handle(bot, update);

        verify(bot).send(MESSAGE_TEXT, update);
    }

    @Test
    @DisplayName("Does nothing on incompatible privacy level")
    void handleNotAllowedDoNothing() throws TelegramApiException {
        Chat chat = new Chat();
        chat.setId(100500L);
        Message message = new Message();
        message.setChat(chat);
        Update update = new Update();
        update.setMessage(message);

        when(config.getOwnerId()).thenReturn(OWNER_ID);

        command.handle(bot, update);

        verify(bot, never()).send(any(), any());
    }
}