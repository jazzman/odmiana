package pro.jazzman.odmiana.bot.replies;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import pro.jazzman.odmiana.bot.OdmianaBot;
import pro.jazzman.odmiana.entities.History;
import pro.jazzman.odmiana.helpers.Localized;
import pro.jazzman.odmiana.services.HistoryService;
import pro.jazzman.odmiana.services.vocabulary.SJP;
import pro.jazzman.odmiana.services.vocabulary.Wikislownik;

/**
 * This class does the hard work of searching and parsing to return all the forms of the word when user enters anything but the command
 */
@Service
@Slf4j
@AllArgsConstructor
public class DefaultReply {
    private Wikislownik wikislownik;
    private SJP sjp;
    private HistoryService historyService;

    public void onMessage(OdmianaBot bot, Update update) throws TelegramApiException {
        String message = message(update);

        bot.send(message, update);
    }

    private String message(Update update) {
        String lang = update.getMessage().getFrom().getLanguageCode();
        String text = update.getMessage().getText().toLowerCase();
        var localized = new Localized(lang);

        Exception exception = null;

        try {
            String word = sjp.get(text);

            return
                wikislownik
                    .page(word)
                    .html()
                    .parse(lang)
                    .message(text);

        } catch (NotFoundException e) {
            log.info(e.getMessage());
            exception = e;
            return localized.message("error.word-not-found") + " " + localized.message("error.change-request");

        } catch (Exception e) {
            log.error("An error occurred: " + e.getMessage());
            exception = e;
            return localized.message("error.word-not-found") + " " + localized.message("error.try-later");
        } finally {
            historyService.create(
                new History(
                    update.getMessage().getFrom().getId(),
                    update.getMessage().getText(),
                    exception != null ? exception.getMessage() : null
                )
            );
        }
    }
}
