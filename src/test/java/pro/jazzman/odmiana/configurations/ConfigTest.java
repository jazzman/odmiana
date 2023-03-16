package pro.jazzman.odmiana.configurations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pro.jazzman.odmiana.bot.LongPollingBot;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Config")
class ConfigTest {
    private static final String USERNAME = "test_bot";
    private static final String TOKEN = "1234567890:AAAAAAA-TMIrr1PZPowv3hNdHkn2eEMnjq8";
    private final Config config = new Config();
    @Test
    @DisplayName("Creates bot")
    void bot() {
        assertThat(config.bot(USERNAME, TOKEN)).isExactlyInstanceOf(LongPollingBot.class);
    }
}