package pro.jazzman.odmiana.configurations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthIndicatorProperties;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.PingHealthIndicator;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import pro.jazzman.odmiana.bot.LongPollingBot;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Config")
@ExtendWith(MockitoExtension.class)
class ConfigTest {
    private static final String USERNAME = "test_bot";
    private static final Long OWNER_ID = 123L;
    private static final String VERSION = "1.2.3";
    private static final String TOKEN = "1234567890:AAAAAAA-TMIrr1PZPowv3hNdHkn2eEMnjq8";
    private final Config config = new Config();
    @Test
    @DisplayName("Creates bot")
    void bot() {
        assertThat(config.bot(USERNAME, TOKEN)).isExactlyInstanceOf(LongPollingBot.class);
    }

    @Test
    @DisplayName("Creates disk space health indicator bean")
    void diskSpaceHealthIndicator(@Mock DiskSpaceHealthIndicatorProperties properties) {
        assertThat(config.diskSpaceHealthIndicator(properties))
            .isExactlyInstanceOf(DiskSpaceHealthIndicator.class);
    }

    @Test
    @DisplayName("Creates ping health indicator bean")
    void pingHealthIndicator() {
        assertThat(config.pingHealthIndicator()).isExactlyInstanceOf(PingHealthIndicator.class);
    }

    @Test
    @DisplayName("Creates mongo health indicator bean")
    void mongoHealthIndicator(@Mock MongoTemplate mongoTemplate) {
        assertThat(config.mongoHealthIndicator(mongoTemplate)).isExactlyInstanceOf(MongoHealthIndicator.class);
    }

    @Test
    @DisplayName("Returns owner id")
    void getOwnerId() {
        config.setOwnerId(OWNER_ID);

        assertThat(config.getOwnerId()).isEqualTo(OWNER_ID);
    }

    @Test
    @DisplayName("Returns version")
    void getVersion() {
        config.setVersion(VERSION);

        assertThat(config.getVersion()).isEqualTo(VERSION);
    }
}