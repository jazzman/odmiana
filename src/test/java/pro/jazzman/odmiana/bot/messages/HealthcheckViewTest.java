package pro.jazzman.odmiana.bot.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.jazzman.odmiana.configurations.Config;
import pro.jazzman.odmiana.configurations.SimpleMongoConfig;
import pro.jazzman.odmiana.entities.Healthcheck;
import pro.jazzman.odmiana.services.HealthcheckService;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthcheckViewTest {
    private static final String VERSION = "1.2.3";

    @Mock private HealthcheckService healthcheckService;
    private HealthcheckView view;

    @BeforeEach
    void setUp() {
        when(healthcheckService.get()).thenReturn(new Healthcheck(VERSION, "OK", "OK"));

        view = new HealthcheckView(healthcheckService);
    }

    @Test
    @DisplayName("Renders a correct healthcheck Telegram message")
    void render() {
        assertThat(view.render()).isEqualTo("""
            Version: *1.2.3*
            Status: *OK*
            Database: *OK*
            """
        );
    }
}