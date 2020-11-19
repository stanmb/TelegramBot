import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    Connection connection = null;
    public void connectEstablish() {
        String host = "jdbc:postgresql://ec2-79-125-64-18.eu-west-1.compute.amazonaws.com:5432/d21sq0lcq6c373";
        String user = "spuruzfkhvpdoa";
        String password = "0462214953ec5f12748074a0a8a7b131478551269c13ddb0b53ce7e3f32ddb32";


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
