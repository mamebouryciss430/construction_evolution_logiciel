package riskboardgame;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Risk {

	public static void main(String[] args) {
		Setup setup = new Setup();

		ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Board(setup.LoadMap(), setup.LoadDeck()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		
		
//		new riskboardgame.Board();

//		try {
//			new riskboardgame.Board();
//
//			new riskboardgame.Board(setup.LoadMap(), setup.setupPlayer(2));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}

	


}
