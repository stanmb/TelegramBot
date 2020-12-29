import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.sql.SQLException;

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

        Bot hoppyBot = new Bot("");
        hoppyBot.sendMsg(361208695L,"HoppyBot successfully started!");
        try {
            hoppyBot.databaseConnect.connection.close();
            if (hoppyBot.databaseConnect.connection.isClosed()) {
                System.out.println("Connection closed");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        hoppyBot = null;
    }


}
