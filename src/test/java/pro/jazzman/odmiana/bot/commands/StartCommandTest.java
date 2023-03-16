package pro.jazzman.odmiana.bot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Start command")
class StartCommandTest {
    @Mock private OdmianaBot bot;
    @Mock private UserService userService;
    private StartCommand command;
    private Update update;

    @BeforeEach
    void init() {
        command = new StartCommand();

        var user = new User();
        user.setId(1L);
        user.setLanguageCode("en");
        user.setFirstName("Lewis");
        user.setLastName("Carrol");

        var message = new Message();
        message.setFrom(user);

        update = new Update();
        update.setMessage(message);

        ReflectionTestUtils.setField(command, "userService", userService);
    }
    @Test
    @DisplayName("Has a correct command")
    void getCommand() {
        assertThat(command.getCommand()).isEqualTo("/start");
    }

    @Test
    @DisplayName("Has a usage example")
    void getUsage() {
        assertThat(command.getUsage()).isEqualTo("/start");
    }

    @Test
    @DisplayName("Has a description")
    void getDescription() {
        assertThat(command.getDescription()).isEqualTo("Launch the bot");
    }

    @Test
    @DisplayName("Sends the message and doesn't create a user if one exist")
    void handleWithExistingUser() throws TelegramApiException {
        when(userService.exists(anyLong())).thenReturn(true);

        command.handle(bot, update);

        verify(userService, times(0)).create(any());
        verify(bot).send(anyString(), eq(update));
    }

    @Test
    @DisplayName("Sends the message and creates a user one doesn't exist")
    void handleWithNewUser() throws TelegramApiException {
        when(userService.exists(anyLong())).thenReturn(false);

        command.handle(bot, update);

        verify(userService).create(any(pro.jazzman.odmiana.entities.users.User.class));
        verify(bot).send(anyString(), eq(update));
    }
}