package pro.jazzman.odmiana.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.interfaces.Command;
import pro.jazzman.odmiana.bot.interfaces.Privacy;
import pro.jazzman.odmiana.bot.messages.HealthcheckView;
import pro.jazzman.odmiana.configurations.Config;

import java.util.Objects;

@Service
public class HealthcheckCommand implements Command {
    @Autowired
    private Config config;

    @Autowired
    private HealthcheckView view;

    @Override
    public String getCommand() {
        return "/healthcheck";
    }

    @Override
    public String getUsage() {
        return "/healthcheck";
    }

    @Override
    public String getDescription() {
        return "Application Healthcheck";
    }

    public Privacy privacy() {
        return Privacy.OWNER;
    }

    @Override
    public void handle(OdmianaBot bot, Update update) throws TelegramApiException {
        if (Objects.equals(config.getOwnerId(), update.getMessage().getChatId())) {
            bot.send(
                view.render(),
                update
            );
        }
    }
}
