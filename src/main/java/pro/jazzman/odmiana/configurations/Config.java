package pro.jazzman.odmiana.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthIndicatorProperties;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.PingHealthIndicator;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import pro.jazzman.odmiana.bot.LongPollingBot;

@Configuration
@Getter
@Setter
public class Config {
    @Value("${bot.owner_id}")
    private Long ownerId;

    @Value("${bot.version}")
    private String version;

    @Value("${wsjp.url}")
    private String dictionaryUrl;

    // TODO: implement DB cache. If this parameter is true than all requested data is saved in the database to decrease external requests and need to parse
    @Value("${bot.cache}")
    private boolean cache;

    @Bean
    public LongPollingBot bot(@Value("${TELEGRAM_USERNAME}") String username, @Value("${TELEGRAM_TOKEN}") String token) {
        return new LongPollingBot(username, token);
    }

    @Bean
    public DiskSpaceHealthIndicator diskSpaceHealthIndicator(DiskSpaceHealthIndicatorProperties properties) {
        return new DiskSpaceHealthIndicator(properties.getPath(), properties.getThreshold());
    }

    @Bean
    public PingHealthIndicator pingHealthIndicator() {
        return new PingHealthIndicator();
    }

    @Bean
    public MongoHealthIndicator mongoHealthIndicator(MongoTemplate mongoTemplate) {
        return new MongoHealthIndicator(mongoTemplate);
    }
}
