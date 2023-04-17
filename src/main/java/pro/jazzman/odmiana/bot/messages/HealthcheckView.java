package pro.jazzman.odmiana.bot.messages;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class HealthcheckView {
    private static final DecimalFormat format = new DecimalFormat("#.##");

    private static final String TEMPLATE = """
        Version: *${version}*
        Application: *${application.status}*
        Database: *${database.status}*
        Disk: *${disk.status}*
        Total Disk Space: *${disk.total}*
        Free Disk Space: *${disk.free}*
        Free Disk Space, %: *${disk.free.percent}*
        """;

    public String render(Health health) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("version", health.getDetails().get("version").toString());
        placeholders.put("application.status", health.getStatus().getCode());
        placeholders.put("ping.status", health.getDetails().get("ping.status").toString());
        placeholders.put("database.status", health.getDetails().get("database.status").toString());
        placeholders.put("disk.status", health.getDetails().get("disk.status").toString());

        Long total = Long.valueOf(health.getDetails().get("disk.total").toString());
        Long free = Long.valueOf(health.getDetails().get("disk.free").toString());
        float percent = (float) free * 100 / total;

        placeholders.put("disk.total", String.valueOf(total));
        placeholders.put("disk.free", String.valueOf(free));
        placeholders.put("disk.free.percent", format.format(percent));


        return StringSubstitutor.replace(TEMPLATE, placeholders);
    }
}
