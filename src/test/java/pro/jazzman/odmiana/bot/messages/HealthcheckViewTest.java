package pro.jazzman.odmiana.bot.messages;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HealthcheckViewTest {
    private final HealthcheckView view = new HealthcheckView();;

    @Test
    @DisplayName("Renders a correct healthcheck Telegram message")
    void render() {

        Health.Builder status = Health.up();
        status.withDetail("version", "1.2.3");
        status.withDetail("ping.status", "UP");
        status.withDetail("database.status", "UP");
        status.withDetail("disk.status", "UP");
        status.withDetail("disk.total", "1000000000");
        status.withDetail("disk.free", "987654321");
        Health health = status.build();

        assertThat(view.render(health)).isEqualTo("""
            Version: *1.2.3*
            Application: *UP*
            Database: *UP*
            Disk: *UP*
            Total Disk Space: *1000000000*
            Free Disk Space: *987654321*
            Free Disk Space, %: *98.77*
            """
        );
    }
}