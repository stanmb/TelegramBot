import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContentKeeper {
    //all beer list initialization
    public  ArrayList<Beer> beerList = new ArrayList<Beer>();
    File file = new File("C:\\Users\\andre\\Desktop\\about.txt");

    //method gets about information from file and returns it in String variable
    public String getAbout() {
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

    public ArrayList<Beer> getListOfBeer1 (Connection connection) {
        String query = "SELECT * FROM beer";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
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

    public void aboutEdit(String about) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(about);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
