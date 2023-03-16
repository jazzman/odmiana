package pro.jazzman.odmiana.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.interfaces.Command;
import pro.jazzman.odmiana.entities.users.User;
import pro.jazzman.odmiana.helpers.Localized;
import pro.jazzman.odmiana.services.UserService;

@Service
public class StartCommand implements Command {
    @Autowired
    private UserService userService;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getUsage() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "Launch the bot";
    }

    @Override
    public void handle(OdmianaBot bot, Update update) throws TelegramApiException {
        var user = update.getMessage().getFrom();

        if (!userService.exists(user.getId())) {
            userService.create(
                new User(user.getId(), user.getFirstName(), user.getLastName(), user.getLanguageCode())
            );
        }

        bot.send(new Localized(update.getMessage().getFrom().getLanguageCode()).message("greeting"), update);
    }
}
