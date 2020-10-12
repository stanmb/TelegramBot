import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Connection;
import java.util.ArrayList;

public class MessageSender {
    public void sendMessage(String text, Connection connection) {
        ArrayList<UserManager> usersList = new UserManager().getUserList(connection);
        for (UserManager user :usersList) {
            new Bot().sendMsg(user.getUserId(),text);

        }

    }
}
