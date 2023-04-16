package pro.jazzman.odmiana.bot.interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;

public interface Command {
    /**
     * @return The command that will trigger @{link onCommandMessage} method
     */
    String getCommand();

    /**
     * @return The usage of the command whenever user types in /command without parameters some commands may return that if requires arguments to be supplied
     */
    String getUsage();

    /**
     * @return The description of the command shown in /help
     */
    String getDescription();

    default Privacy privacy() {
        return Privacy.PUBLIC;
    }

    /**
     * Fired when user types in /command arg0 arg1 arg2..
     *
     * @param update  the update
     * @throws TelegramApiException the exception
     */
    void handle(OdmianaBot bot, Update update) throws TelegramApiException;
}
