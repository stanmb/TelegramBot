import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {

    long userId = 0;
    String userName = null;
    boolean isSubscribed = true;
    String query = null;

    public UserManager() {
    }

    UserManager(Message message) {
        this.userId = message.getChat().getId();
        this.userName = message.getChat().getUserName();
        this.isSubscribed = true;
    }
    public void userCheck(Connection connection) {
        query = "INSERT INTO users(user_id,user_name,is_subscribed) VALUES(?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,this.userId);
            preparedStatement.setString(2,this.userName);
            preparedStatement.setBoolean(3,this.isSubscribed);

            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Long> getUserList(Connection connection) {
        query = "SELECT user_id from users";
        ArrayList<Long> userList = new ArrayList<Long>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(resultSet.getLong("user_id"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
