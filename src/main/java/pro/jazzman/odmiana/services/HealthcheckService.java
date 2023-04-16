package pro.jazzman.odmiana.services;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.configurations.Config;
import pro.jazzman.odmiana.configurations.SimpleMongoConfig;
import pro.jazzman.odmiana.entities.Healthcheck;

@Service
public class HealthcheckService {
    private static final String STATUS_FAILED = "FAILED";
    private static final String STATUS_OK = "OK";

    private final Config config;
    private final SimpleMongoConfig mongoConfig;
    @Autowired
    public HealthcheckService(Config config, SimpleMongoConfig mongoConfig) {
        this.config = config;
        this.mongoConfig = mongoConfig;
    }

    public Healthcheck get() {
        return new Healthcheck(config.getVersion(), status(), databaseStatus());
    }

    private String databaseStatus() {
        try (MongoClient client = mongoConfig.mongo()) {
            return STATUS_OK;
        } catch (Exception e) {
            return STATUS_FAILED;
        }
    }

    /**
     * Summary status of the application. Only checking database connection for now.
     * @return OK if application is alive, FAILED - otherwise
     */
    private String status() {
        return STATUS_OK.equals(databaseStatus()) ? STATUS_OK : STATUS_FAILED;
    }
}
