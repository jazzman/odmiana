package pro.jazzman.odmiana.bot;

import com.github.unafraid.telegrambot.bots.AbstractTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import pro.jazzman.odmiana.bot.interfaces.Command;
import pro.jazzman.odmiana.bot.services.Commands;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Odmiana Bot")
class OdmianaBotTest {
    private static final String COMMAND = "/command";
    private static final String NOT_COMMAND = "notcommand";

    private OdmianaBot odmianaBot;
    private final Update update = new Update();
    private final Message message = new Message();

    @Mock private AbstractTelegramBot bot;
    @Mock private Commands commands;
    @Mock private DefaultBotOptions botOptions;

    @BeforeEach
    void setUp() throws TelegramApiException {
        odmianaBot = new OdmianaBot(bot, commands);

        var user = new User();
        var chat = new Chat();
        chat.setId(1L);

        message.setFrom(user);
        message.setChat(chat);
        update.setMessage(message);
    }

    @Test
    @DisplayName("SetMyCommands message is being sent")
    void setMyCommands() throws TelegramApiException {
        odmianaBot.setMyCommands();

        verify(bot).execute(any(SetMyCommands.class));
    }

    @Test
    @DisplayName("Sends tuned message")
    void send() throws TelegramApiException {
        odmianaBot.send("test", update);

        verify(bot).execute(getExpectedMessage());
    }

    @Test
    @DisplayName("Handles the command from the update")
    void onUpdateReceivedCommand(@Mock Command command) throws TelegramApiException {
        doNothing().when(command).handle(any(), any());
        message.setText(COMMAND);
        when(commands.isCommand(anyString())).thenReturn(true);
        when(commands.getBy(COMMAND)).thenReturn(command);

        odmianaBot.onUpdateReceived(update);

        verify(command).handle(any(), any());
    }

    @Test
    @DisplayName("Handles the update if not a command")
    void onUpdateReceivedNotCommand() throws TelegramApiException {
        message.setText(NOT_COMMAND);
        when(commands.isCommand(anyString())).thenReturn(false);

        odmianaBot.onUpdateReceived(update);

        verify(commands).onReply(any(), any());
    }

    @Test
    @DisplayName("Throws exception if error occurred")
    void onUpdateReceivedCommandThrowsException(@Mock Command command) throws TelegramApiException {
        doThrow(TelegramApiException.class).when(command).handle(any(), any());
        message.setText(COMMAND);
        when(commands.isCommand(anyString())).thenReturn(true);
        when(commands.getBy(COMMAND)).thenReturn(command);

        assertThatThrownBy(() -> odmianaBot.onUpdateReceived(update)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Does nothing if no words in the message")
    void onUpdateReceivedIfNoWordsDoNothing() {
        message.setText("");

        assertThatNoException().isThrownBy(() -> odmianaBot.onUpdateReceived(update));
    }

    @Test
    @DisplayName("Does nothing if no message in update")
    void onUpdateReceivedIfNoMessageDoNothing() {
        assertThatNoException().isThrownBy(() -> odmianaBot.onUpdateReceived(new Update()));
    }

    @Test
    @DisplayName("Returns the same options as a 'parent' bot")
    void getOptions() {
        when(bot.getOptions()).thenReturn(botOptions);

        assertThat(odmianaBot.getOptions()).isEqualTo(botOptions);
    }

    @Test
    @DisplayName("Calls the 'clearWebhooks' as a 'parent' bot")
    void clearWebhook() throws TelegramApiRequestException {
        odmianaBot.clearWebhook();

        verify(bot).clearWebhook();
    }

    @Test
    @DisplayName("Returns bot username")
    void getBotUsername() {
        when(bot.getBotUsername()).thenReturn("username");

        assertThat(odmianaBot.getBotUsername()).isEqualTo("username");
    }

    @Test
    @DisplayName("Returns bot token")
    void getBotToken() {
        when(bot.getBotToken()).thenReturn("token");

        assertThat(odmianaBot.getBotToken()).isEqualTo("token");
    }

    private SendMessage getExpectedMessage() {
        var message = new SendMessage();
        message.setChatId(Long.toString(1L));
        message.setText("test");
        message.enableMarkdown(true);
        message.setReplyMarkup(null);
        message.disableWebPagePreview();

        return message;
    }
}