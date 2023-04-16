package pro.jazzman.odmiana.services;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.jazzman.odmiana.configurations.Config;
import pro.jazzman.odmiana.configurations.SimpleMongoConfig;
import pro.jazzman.odmiana.entities.Healthcheck;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthcheckServiceTest {
    private static final String VERSION = "1.2.3";

    @Mock private Config config;
    @Mock private SimpleMongoConfig mongoConfig;
    @Mock private MongoClient mongoClient;

    private HealthcheckService service;

    @BeforeEach
    void setUp() {
        when(config.getVersion()).thenReturn(VERSION);
        when(mongoConfig.mongo()).thenReturn(mongoClient);

        service = new HealthcheckService(config, mongoConfig);
    }

    @Test
    @DisplayName("Returns healthy status")
    void get() {
        assertThat(service.get()).isEqualTo(new Healthcheck(VERSION, "OK", "OK"));
    }

    @Test
    @DisplayName("Returns failed status")
    void getDatabaseConnectionFailsReturnsFailedStatus() {
        when(mongoConfig.mongo()).thenThrow(MongoClientException.class);

        assertThat(service.get()).isEqualTo(new Healthcheck(VERSION, "FAILED", "FAILED"));
    }
}