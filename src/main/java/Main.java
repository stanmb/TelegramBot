import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Initialize Api Context
        ApiContextInitializer.init();
        // Instantiate Telegram Bots API
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        // Register our bot
        try {
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        System.out.println("HoppyBot successfully started!");


    }
}
