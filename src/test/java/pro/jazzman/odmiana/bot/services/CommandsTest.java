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
import pro.jazzman.odmiana.bot.interfaces.Command;
import pro.jazzman.odmiana.bot.interfaces.Privacy;
import pro.jazzman.odmiana.bot.replies.DefaultReply;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Commands")
class CommandsTest {
    private Commands commands;

    @Mock private DefaultReply defaultReply;

    @BeforeEach
    void init() {
        commands = new Commands(new Command1(), new Command2(), new Command3());

        ReflectionTestUtils.setField(commands, "defaultReply", defaultReply);
    }

    @Test
    @DisplayName("List of BotCommands")
    void asBotCommands() {
        assertThat(commands.asBotCommands()).containsExactly(
            new BotCommand("/command1", "Sample command 1"),
            new BotCommand("/command2", "Sample command 2"),
            new BotCommand("/command3", "Sample command 3")
        );
    }

    @Test
    @DisplayName("List of BotCommands with restricted access")
    void asBotCommandsRestrictedAccess() {
        assertThat(commands.asBotCommands(Privacy.PUBLIC)).containsExactly(
            new BotCommand("/command1", "Sample command 1"),
            new BotCommand("/command2", "Sample command 2")
        );
    }

    @Test
    @DisplayName("Checks if string is a command")
    void isCommand() {
        assertThat(commands.isCommand("/command1")).isTrue();
    }

    @Test
    @DisplayName("Checks arbitrary string is not a command")
    void isCommandNotCommand() {
        assertThat(commands.isCommand("/notacommand")).isFalse();
    }

    @Test
    @DisplayName("Returns a command by name")
    void getBy() {
        assertThat(commands.getBy("/command1")).isExactlyInstanceOf(Command1.class);
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

    private static class Command1 implements Command {
        @Override
        public String getCommand() {
            return "/command1";
        }

        @Override
        public String getUsage() {
            return "/command1";
        }

        @Override
        public String getDescription() {
            return "Sample command 1";
        }

        @Override
        public void handle(OdmianaBot bot, Update update) {
        }
    }

    private static class Command2 implements Command {
        @Override
        public String getCommand() {
            return "/command2";
        }

        @Override
        public String getUsage() {
            return "/command2";
        }

        @Override
        public String getDescription() {
            return "Sample command 2";
        }

        @Override
        public void handle(OdmianaBot bot, Update update) {
        }
    }

    private static class Command3 implements Command {
        @Override
        public String getCommand() {
            return "/command3";
        }

        @Override
        public String getUsage() {
            return "/command3";
        }

        @Override
        public String getDescription() {
            return "Sample command 3";
        }

        @Override
        public Privacy privacy() {
            return Privacy.OWNER;
        }

        @Override
        public void handle(OdmianaBot bot, Update update) {
        }
    }
}