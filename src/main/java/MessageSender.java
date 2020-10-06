import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Connection;
import java.util.ArrayList;

public class MessageSender {
    Bot bot = new Bot();
    public void sendMessage(String text, Connection connection) {
        UserManager users = new UserManager();
        ArrayList<UserManager> usersList = users.getUserList(connection);
        for (UserManager user :usersList) {
            bot.sendMsg(user.getUserId(),text);
        }

    }
}
