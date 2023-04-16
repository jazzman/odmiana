package pro.jazzman.odmiana.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.jazzman.odmiana.bot.LongPollingBot;

@Configuration
@Getter
@Setter
public class Config {
    @Value("${bot.owner_id}")
    private Long ownerId;

    @Value("${bot.version}")
    private String version;

    @Bean
    public LongPollingBot bot(@Value("${TELEGRAM_USERNAME}") String username, @Value("${TELEGRAM_TOKEN}") String token) {
        return new LongPollingBot(username, token);
    }
}
