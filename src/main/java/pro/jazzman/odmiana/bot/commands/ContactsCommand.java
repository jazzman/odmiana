package pro.jazzman.odmiana.bot.commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.interfaces.Command;
import pro.jazzman.odmiana.helpers.Localized;

@Service
public class ContactsCommand implements Command {
    @Override
    public String getCommand() {
        return "/contacts";
    }

    @Override
    public String getUsage() {
        return "/contacts";
    }

    @Override
    public String getDescription() {
        return "Get in touch";
    }

    @Override
    public void handle(OdmianaBot bot, Update update) throws TelegramApiException {
        bot.send(
            new Localized(update.getMessage().getFrom().getLanguageCode())
                .message("contact-me", "odmiana.bot@gmail.com"),
            update
        );
    }
}
