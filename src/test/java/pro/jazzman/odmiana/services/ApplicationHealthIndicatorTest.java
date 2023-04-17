package pro.jazzman.odmiana.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.PingHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.test.util.ReflectionTestUtils;
import pro.jazzman.odmiana.configurations.Config;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationHealthIndicatorTest {
    private static final String VERSION = "1.2.3";
    private static final String UP = "UP";
    private static final String DOWN = "DOWN";
    private static final String TOTAL_SPACE = "1000000000";
    private static final String FREE_SPACE = "987654321";

    private ApplicationHealthIndicator indicator;

    @Mock private MongoHealthIndicator mongoHealthIndicator;
    @Mock private PingHealthIndicator pingHealthIndicator;
    @Mock private Config config;
    @Mock private DiskSpaceHealthIndicator diskSpaceHealthIndicator;

    @BeforeEach
    void setUp() {
        indicator = new ApplicationHealthIndicator();

        when(pingHealthIndicator.health()).thenReturn(Health.up().build());
        when(mongoHealthIndicator.health()).thenReturn(Health.up().build());
        when(diskSpaceHealthIndicator.health())
            .thenReturn(Health.up().withDetails(Map.of(
                "total", TOTAL_SPACE,
                "free", FREE_SPACE
            )).build());

        when(config.getVersion()).thenReturn(VERSION);

        ReflectionTestUtils.setField(indicator, "mongoHealthIndicator", mongoHealthIndicator);
        ReflectionTestUtils.setField(indicator, "pingHealthIndicator", pingHealthIndicator);
        ReflectionTestUtils.setField(indicator, "diskSpaceHealthIndicator", diskSpaceHealthIndicator);
        ReflectionTestUtils.setField(indicator, "config", config);
    }

    @Test
    @DisplayName("Returns UP status if all services are UP")
    void health() {
        Health health = indicator.health();
        assertThat(health.getDetails().get("version")).isEqualTo(VERSION);
        assertThat(health.getDetails().get("ping.status")).isEqualTo(UP);
        assertThat(health.getDetails().get("database.status")).isEqualTo(UP);
        assertThat(health.getDetails().get("disk.status")).isEqualTo(UP);
        assertThat(health.getDetails().get("disk.total")).isEqualTo(TOTAL_SPACE);
        assertThat(health.getDetails().get("disk.free")).isEqualTo(FREE_SPACE);
        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

    @Test
    @DisplayName("Returns DOWN status if on of the services is DOWN")
    void healthServiceDownReturnsDown() {
        when(mongoHealthIndicator.health()).thenReturn(Health.down().build());

        Health health = indicator.health();
        assertThat(health.getDetails().get("version")).isEqualTo(VERSION);
        assertThat(health.getDetails().get("ping.status")).isEqualTo(UP);
        assertThat(health.getDetails().get("database.status")).isEqualTo(DOWN);
        assertThat(health.getDetails().get("disk.status")).isEqualTo(UP);
        assertThat(health.getDetails().get("disk.total")).isEqualTo(TOTAL_SPACE);
        assertThat(health.getDetails().get("disk.free")).isEqualTo(FREE_SPACE);
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
    }
}