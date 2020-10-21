import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageSender {
    public void sendMessage(String text, Connection connection) {
        Bot hoppyBot = new Bot("");
        ArrayList<UserManager> usersList = new UserManager().getUserList(connection);
        for (UserManager user :usersList) {
                hoppyBot.sendMsg(user.getUserId(),text);
        }
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
