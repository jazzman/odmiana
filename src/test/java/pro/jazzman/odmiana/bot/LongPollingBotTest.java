package pro.jazzman.odmiana.bot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@DisplayName("TelegramLongPollingBot")
class LongPollingBotTest {
    private static final String USERNAME = "test_bot";
    private static final String TOKEN = "1234567890:AAAAAAA-TMIrr1PZPowv3hNdHkn2eEMnjq8";

    private final LongPollingBot bot = new LongPollingBot(USERNAME, TOKEN);
    @Test
    @DisplayName("Does nothing on update received")
    void onUpdateReceived() {
        assertThatThrownBy(() -> bot.onUpdateReceived(new Update())).isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Returns username")
    void getBotUsername() {
        assertThat(bot.getBotUsername()).isEqualTo(USERNAME);
    }
}