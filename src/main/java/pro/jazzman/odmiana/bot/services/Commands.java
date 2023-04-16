package pro.jazzman.odmiana.bot.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.bot.interfaces.Privacy;
import pro.jazzman.odmiana.bot.replies.DefaultReply;
import pro.jazzman.odmiana.bot.interfaces.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@NoArgsConstructor
public class Commands {
    private List<Command> items = new ArrayList<>();

    @Autowired
    private DefaultReply defaultReply;

    @Autowired
    public Commands(ApplicationContext context) {
        this.items = context.getBeansOfType(Command.class).values().stream().toList();
    }

    public Commands(List<Command> items) {
        this.items = items;
    }

    public Commands(Command... items) {
        this(Arrays.stream(items).toList());
    }

    public List<BotCommand> asBotCommands() {
        return items.stream().map(c -> new BotCommand(c.getCommand(), c.getDescription())).toList();
    }

    public List<BotCommand> asBotCommands(Privacy privacy) {
        return items.stream().filter(c -> c.privacy() == privacy).map(c -> new BotCommand(c.getCommand(), c.getDescription())).toList();
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
