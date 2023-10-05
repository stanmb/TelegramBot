import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    Connection connection = null;
    public void connectEstablish() {
        String username = "stanmb";
        String password = "XwHsNVwLNoL4";
        String dbUrl = "jdbc:postgresql://185.240.103.224:5432/tel_bots";

        // Establish connection to DB
        try {
            connection = DriverManager.getConnection(dbUrl, username, password);
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
        }
    }
}
