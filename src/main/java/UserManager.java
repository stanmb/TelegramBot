import org.postgresql.core.SqlCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private long userId = 0;
    String firstName = null;
    String lastName = null;
    String userName = null;
    boolean isSubscribed = true;
    Date dateOfJoin = null;
    String query = null;

    public long getUserId() {
        return userId;
    }

    public UserManager() {
    }

    UserManager(Message message) {
        this.userId = message.getChat().getId();
        this.userName = message.getChat().getUserName();
        this.isSubscribed = true;
        this.firstName = message.getChat().getFirstName();
        this.lastName = message.getChat().getLastName();
        this.dateOfJoin = new Date(new java.util.Date().getTime());
    }
    UserManager(Long userId, String firstName, String lastName, String userName, Boolean isSubscribed, Date dateOfJoin) {
        this.userId = userId;
        this.userName = userName;
        this.isSubscribed = isSubscribed;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfJoin = dateOfJoin;
    }
    public void userCheck(Connection connection) {
        query = "INSERT INTO users(user_id,user_name,is_subscribed,first_name,last_name,date_of_join) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,this.userId);
            preparedStatement.setString(2,this.userName);
            preparedStatement.setBoolean(3,this.isSubscribed);
            preparedStatement.setString(4,this.firstName);
            preparedStatement.setString(5,this.lastName);
            preparedStatement.setDate(6,this.dateOfJoin);


            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<UserManager> getUserList(Connection connection) {
        query = "SELECT * from users";
        ArrayList<UserManager> userList = new ArrayList<UserManager>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getBoolean("is_subscribed")) {
                    userList.add(new UserManager(resultSet.getLong("user_id"), resultSet.getString("first_name"),
                            resultSet.getString("last_name"), resultSet.getString("user_name"),
                            resultSet.getBoolean("is_subscribed"), resultSet.getDate("date_of_join")));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public ArrayList<Long> getUsersIds(Connection connection) {
        ArrayList<Long> listOfIds = new ArrayList<Long>();
        ArrayList<UserManager> users = new UserManager().getUserList(connection);
        for (UserManager user :users) {
            listOfIds.add(user.userId);
        }
        return  listOfIds;
    }
    public HashMap<Long,Boolean> getUsersIdAndSub(Connection connection) {
        HashMap<Long,Boolean> usersIdHashMap = new HashMap<Long,Boolean>();
        ArrayList<UserManager> users = new UserManager().getUserList(connection);
        for (UserManager user :users) {
            usersIdHashMap.put(user.userId,user.isSubscribed);
        }
        return  usersIdHashMap;
    }

    public int getNumberOfUsers(Connection connection) {
        query = "SELECT * from users";
        int result = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if ((resultSet.getBoolean("is_subscribed"))) {
                    result++;
                }

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public  void setIsSubscribedFalse(Connection connection, Long chatId) {
        query = "UPDATE users SET is_subscribed = false where user_id = (?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,chatId);

            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void setIsSubscribedTrue(Connection connection, Long chatId) {
        query = "UPDATE users SET is_subscribed = true where user_id = (?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1,chatId);

            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String string = "Никнейм: " + this.userName + "," + " Имя: " + this.firstName + "," + " Фамилия: "
                + this.lastName + "," + " Подписался с: " + this.dateOfJoin;
        return string;
    }
}
