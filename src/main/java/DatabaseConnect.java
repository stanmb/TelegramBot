import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    Connection connection = null;
    public void connectEstablish() {
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";



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
