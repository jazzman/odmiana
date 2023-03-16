package pro.jazzman.odmiana.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.commands.ContactsCommand;
import pro.jazzman.odmiana.bot.commands.StartCommand;
import pro.jazzman.odmiana.bot.replies.DefaultReply;
import pro.jazzman.odmiana.bot.interfaces.Command;

import java.util.List;

@Service
public class Commands {
    private final List<Command> items;

    @Autowired
    private DefaultReply defaultReply;

    public Commands() {
        items = List.of(
            new StartCommand(),
            new ContactsCommand()
        );
    }

    public List<BotCommand> asBotCommands() {
        return items.stream().map(c -> new BotCommand(c.getCommand(), c.getDescription())).toList();
    }

    public boolean isCommand(String command) {
        return items.stream().anyMatch(c -> command.equals(c.getCommand()));
    }

    public Command getBy(String name) {
        return items.stream().filter(c -> name.equals(c.getCommand())).findFirst().orElse(null);
    }

    public void onReply(OdmianaBot bot, Update update) throws TelegramApiException {
        defaultReply.onMessage(bot, update);
    }
}
