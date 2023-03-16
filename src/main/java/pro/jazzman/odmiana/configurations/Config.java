package pro.jazzman.odmiana.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.jazzman.odmiana.bot.LongPollingBot;

@Configuration
public class Config {
    @Bean
    public LongPollingBot bot(@Value("${TELEGRAM_USERNAME}") String username, @Value("${TELEGRAM_TOKEN}") String token) {
        return new LongPollingBot(username, token);
    }
}
