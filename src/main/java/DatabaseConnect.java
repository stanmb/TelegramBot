import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    Connection connection = null;
    public void connectEstablish() {
        String host = "jdbc:postgresql://127.0.0.1:5432/hoppydb";
        String user = "postgres";
        String password = "admin1";


        // Establish connection to DB
        try {
            connection = DriverManager.getConnection(host, user, password);
            if (connection != null) {
                System.out.println("Got connection");
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
           Bot hoppyBot = new Bot("plain");
           hoppyBot.sendMsg(361208695L,"Connection to DB is failed!!!");
           try {
              hoppyBot.databaseConnect.connection.close();
              if (hoppyBot.databaseConnect.connection.isClosed()) {
                 System.out.println("Connection closed");
              }
           }
           catch (SQLException ex) {
              ex.printStackTrace();
           }
            e.printStackTrace();
            return;
        }
    }
    public boolean isConnected (Connection connection) {
        boolean result = false;
        try {
            result = connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
