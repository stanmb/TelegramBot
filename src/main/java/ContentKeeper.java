import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContentKeeper {
    //all beer list initialization
    public  ArrayList<Beer> beerList = new ArrayList<Beer>();

    //method returns a list of all beer
   /* public ArrayList<Beer> getListOfBeer(File file) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                addBear(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beerList;
    }
*/
/*    //method parses line from file with beer, makes new object Beer and add it to the list of all beer
    public void addBear(String lineToPars) {
        String[] line = lineToPars.split("/");
        Beer beer = new Beer(line[0],line[1],line[2]);
        beerList.add(beer);
    }*/
    //method gets about information from file and returns it in String variable
    public String getAbout(File file) {
        String about = "";
        String nextLine;
        try  {
            BufferedReader reader = new BufferedReader( new FileReader(file));
            while((nextLine = reader.readLine()) != null) {
                about += nextLine;
                about += "\n" + "\n" + "\n";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return about;
    }

    public ArrayList<Beer> getListOfBeer1 () {
        String query = "SELECT * FROM beer";
        SqlGetConnect sqlGetConnect = new SqlGetConnect();
        sqlGetConnect.connectEstablish();
        //Connection connection = sqlGetConnect.connection;
        try (Connection connection = sqlGetConnect.connection; PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Beer beer = new Beer(resultSet.getString("name"),resultSet.getString("vol"),resultSet.getString("price"));
                beerList.add(beer);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return beerList;
    }
}
