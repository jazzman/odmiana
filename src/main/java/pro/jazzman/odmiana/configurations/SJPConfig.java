package pro.jazzman.odmiana.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sjp")
@Getter
@Setter
public class SJPConfig {
    private String url;
}
