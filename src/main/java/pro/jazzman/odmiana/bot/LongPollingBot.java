package pro.jazzman.odmiana.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class LongPollingBot extends TelegramLongPollingBot {
    private final String username;

    public LongPollingBot(String username, String token) {
        super(token);
        this.username = username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        throw new UnsupportedOperationException("To be implemented in the child class");
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
