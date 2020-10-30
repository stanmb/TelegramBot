import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Counter implements Runnable {
    int beer;
    int snacks;
    int about;
    Connection connection;
    boolean isSentToAdmins;

    public Counter(int beer, int snacks, int about, Connection connection, boolean isSentToAdmins) {
        this.beer = beer;
        this.snacks = snacks;
        this.about = about;
        this.connection = connection;
        this.isSentToAdmins = isSentToAdmins;
    }

    @Override
    public void run() {
        if (this.isSentToAdmins) {
            sendCountersToAdmins(connection);
        }
        else {
            sendCountersToDB(beer,snacks,about,connection);
        }
    }

    public void sendCountersToAdmins(Connection connection) {
        int beer = 0;
        int snacks = 0;
        int about = 0;
        long[] adminIds = {361208695L};
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
        
    }
}
