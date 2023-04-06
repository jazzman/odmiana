package pro.jazzman.odmiana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import pro.jazzman.odmiana.bot.OdmianaBot;

@SpringBootApplication
public class OdmianaApplication implements CommandLineRunner {
	@Autowired private LongPollingBot odmianaBot;

	public static void main(String[] args) {
		SpringApplication.run(OdmianaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new TelegramBotsApi(DefaultBotSession.class).registerBot(odmianaBot);

		((OdmianaBot) odmianaBot).setMyCommands();
	}
}
