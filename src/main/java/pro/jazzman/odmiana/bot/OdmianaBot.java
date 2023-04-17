package pro.jazzman.odmiana.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import pro.jazzman.odmiana.bot.interfaces.Privacy;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
import pro.jazzman.odmiana.bot.services.Commands;

import java.util.*;

@Component
@Slf4j
public class OdmianaBot implements LongPollingBot {
    private final TelegramLongPollingBot bot;
    private final Commands commands;

    @Autowired
    public OdmianaBot(TelegramLongPollingBot bot, Commands commands) {
        this.bot = bot;
        this.commands = commands;
    }

    public void setMyCommands() throws TelegramApiException {
        bot.execute(
            new SetMyCommands(commands.asBotCommands(Privacy.PUBLIC), new BotCommandScopeDefault(), "en")
        );
    }

    public void send(String text, Update update) throws TelegramApiException {
        final SendMessage msg = new SendMessage();

        msg.setChatId(Long.toString(update.getMessage().getChat().getId()));
        msg.setText(text);
        msg.enableMarkdown(true);
        msg.setReplyMarkup(null);
        msg.disableWebPagePreview();

        bot.execute(msg);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String text = update.getMessage().getText();

            log.debug("Message received: '{}' [user: {}, update: {}]'", text, update.getMessage().getFrom().getId(), update.getUpdateId());

            List<String> words = Arrays.stream(text.split("\\s+")).filter(s -> !s.isEmpty()).toList();

            if (words.isEmpty()) {
                return;
            }

            String firstWord = words.get(0);

            try {
                if (commands.isCommand(firstWord)) {
                    commands.getBy(firstWord).handle(this, update);
                } else {
                    commands.onReply(this, update);
                }

            } catch (TelegramApiException e) {
                throw new ApplicationRuntimeException(e);
            }

            log.debug("Sent result for '{}' [user: {}, update: {}]", text, update.getMessage().getFrom().getId(), update.getUpdateId());
        }
    }

    @Override
    public BotOptions getOptions() {
        return bot.getOptions();
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        bot.clearWebhook();
    }

    @Override
    public String getBotUsername() {
        return bot.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return bot.getBotToken();
    }
}
