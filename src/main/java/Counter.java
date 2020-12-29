import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Counter implements Runnable {
    int beer;
    int snacks;
    int about;
    Connection connection;
    Method method;

    public Counter(int beer, int snacks, int about, Connection connection, Method method) {
        this.beer = beer;
        this.snacks = snacks;
        this.about = about;
        this.connection = connection;
        this.method = method;
    }

    @Override
    public void run() {
        switch (this.method) {
            case ADM:
                sendCountersToAdmins(connection);
                clearDB(connection);
                break;
            case BD:
                sendCountersToDB(beer,snacks,about,connection);
                break;
        }
    }

    public void sendCountersToAdmins(Connection connection) {
        int beer = 0;
        int snacks = 0;
        int about = 0;
        long[] adminIds = {361208695L,337817426L};
        String query = "SELECT * FROM counters_data where id = 1";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                beer = resultSet.getInt("beer");
                snacks = resultSet.getInt("snacks");
                about = resultSet.getInt("about");
            }
            for (Long admin :adminIds) {
                new Bot("s").sendMsg(admin,"Число запросов кранов: " + beer + "\n" + "Число запросов закусок: "
                        + snacks + "\n" + "Число запросов О нас: " + about);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void sendCountersToDB(int beer, int snacks, int about, Connection connection) {
        String query = "UPDATE counters_data SET beer = beer + (?), snacks = snacks + (?), about = about + (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,beer);
            preparedStatement.setInt(2,snacks);
            preparedStatement.setInt(3,about);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void clearDB(Connection connection) {
        String query = "UPDATE counters_data SET beer = 0, snacks = 0, about = 0";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
