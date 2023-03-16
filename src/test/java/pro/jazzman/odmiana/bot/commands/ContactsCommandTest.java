package pro.jazzman.odmiana.bot.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Contacts command")
class ContactsCommandTest {
    @Mock private OdmianaBot bot;
    private ContactsCommand command;

    @BeforeEach
    void init() {
        command = new ContactsCommand();
    }

    @Test
    @DisplayName("Has a correct command")
    void getCommand() {
        assertThat(command.getCommand()).isEqualTo("/contacts");
    }

    @Test
    @DisplayName("Has a usage example")
    void getUsage() {
        assertThat(command.getUsage()).isEqualTo("/contacts");
    }

    @Test
    @DisplayName("Has a description")
    void getDescription() {
        assertThat(command.getDescription()).isEqualTo("Get in touch");
    }

    @Test
    @DisplayName("Sends a message")
    void handle() throws TelegramApiException {
        var from = new User();
        var message = new Message();
        var update = new Update();

        from.setLanguageCode("en");
        message.setFrom(from);
        update.setMessage(message);

        command.handle(bot, update);

        verify(bot).send(anyString(), eq(update));
    }
}