package pro.jazzman.odmiana.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.PingHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.stereotype.Component;
import pro.jazzman.odmiana.configurations.Config;

@Component
public class ApplicationHealthIndicator implements HealthIndicator {
    @Autowired
    private PingHealthIndicator pingHealthIndicator;

    @Autowired
    private MongoHealthIndicator mongoHealthIndicator;

    @Autowired
    private DiskSpaceHealthIndicator diskSpaceHealthIndicator;

    @Autowired
    private Config config;


    @Override
    public Health health() {
        Health.Builder status = Health.up();

        Status ping = pingHealthIndicator.health().getStatus();
        Status database = mongoHealthIndicator.health().getStatus();
        Health diskSpaceHealth = diskSpaceHealthIndicator.health();

        status.withDetail("version", config.getVersion());
        status.withDetail("ping.status", ping.getCode());
        status.withDetail("database.status", database.getCode());
        status.withDetail("disk.status", diskSpaceHealth.getStatus().getCode());
        status.withDetail("disk.total", diskSpaceHealth.getDetails().get("total"));
        status.withDetail("disk.free", diskSpaceHealth.getDetails().get("free"));

        if (ping.equals(Status.DOWN) || database.equals(Status.DOWN) || diskSpaceHealth.getStatus().equals(Status.DOWN)) {
            status.down();
        }

        return status.build();
    }
}
