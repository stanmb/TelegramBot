import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlGetConnect {
    Connection connection = null;
    public void connectEstablish() {
        String host = "jdbc:postgresql://127.0.0.1:5432/hoppydb";
        String user = "postgres";
        String password = "admin";
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
}
