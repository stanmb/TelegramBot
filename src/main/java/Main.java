import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        // Instantiate Telegram Bots API
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        // Register our bot
        try {
            telegramBotsApi.registerBot(new Bot());
        }
        catch (TelegramApiException ex) {
            ex.printStackTrace();
            Bot hoppyBot = new Bot("s");
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
        }
        System.out.println("HoppyBot successfully started!");

        Bot hoppyBot = new Bot("s");
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
