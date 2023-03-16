package pro.jazzman.odmiana.bot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.commands.StartCommand;
import pro.jazzman.odmiana.bot.replies.DefaultReply;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Commands")
class CommandsTest {
    private Commands commands;

    @Mock DefaultReply defaultReply;

    @BeforeEach
    void init() {
        commands = new Commands();

        ReflectionTestUtils.setField(commands, "defaultReply", defaultReply);
    }

    @Test
    @DisplayName("List of BotCommands")
    void asBotCommands() {
        assertThat(commands.asBotCommands()).containsExactly(
            new BotCommand("/start", "Launch the bot"),
            new BotCommand("/contacts", "Get in touch")
        );
    }

    @Test
    @DisplayName("Checks if string is a command")
    void isCommand() {
        assertThat(commands.isCommand("/start")).isTrue();
    }

    @Test
    @DisplayName("Checks arbitrary string is not a command")
    void isCommandNotCommand() {
        assertThat(commands.isCommand("/notacommand")).isFalse();
    }

    @Test
    @DisplayName("Returns a command by name")
    void getBy() {
        assertThat(commands.getBy("/start")).isExactlyInstanceOf(StartCommand.class);
    }

    @Test
    @DisplayName("Returns null if there is no command with such a name")
    void getByNotFound() {
        assertThat(commands.getBy("/notacommand")).isNull();
    }

    @Test
    @DisplayName("Default reply is being sent")
    void onReply(@Mock OdmianaBot bot, @Mock Update update) throws TelegramApiException {
        commands.onReply(bot, update);

        verify(defaultReply).onMessage(bot, update);
    }
}