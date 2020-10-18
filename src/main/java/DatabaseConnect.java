import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    Connection connection = null;
    public void connectEstablish() {
        /*String host = "jdbc:postgresql://127.0.0.1:5432/hoppydb";
        String user = "postgres";
        String password = "admin";*/
        String host = "jdbc:postgresql://ec2-46-137-124-19.eu-west-1.compute.amazonaws.com:5432/d77k9gdfou0i1g";
        String user = "wahscetyorslav";
        String password = "1b92a43e7a775293b7c18f89f920f34f656ef93830ffe939d734cf12326383bd";
        // Establish connection to DB
        try {
            connection = DriverManager.getConnection(host, user, password);
            if (connection != null) {
                System.out.println("Got connection");
            }

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
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
