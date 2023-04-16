package pro.jazzman.odmiana.bot.messages;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.Healthcheck;
import pro.jazzman.odmiana.services.HealthcheckService;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthcheckView {
    private final HealthcheckService service;

    @Autowired
    public HealthcheckView(HealthcheckService service) {
        this.service = service;
    }

    private static final String TEMPLATE = """
        Version: *${version}*
        Status: *${application.status}*
        Database: *${database.status}*
        """;

    public String render() {
        Healthcheck healthcheck = service.get();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("version", healthcheck.getVersion());
        placeholders.put("application.status", healthcheck.getStatus());
        placeholders.put("database.status", healthcheck.getDatabase());

        return StringSubstitutor.replace(TEMPLATE, placeholders);
    }
}
