import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContentKeeper {
    //all beer list initialization
    public ArrayList<Beer> beerList = new ArrayList<Beer>();
    File file = new File("C:\\Users\\andre\\Desktop\\about.txt");

    //method gets about information from file and returns it in String variable
    public String getAbout() {
        String about = "";
        String nextLine;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((nextLine = reader.readLine()) != null) {
                about += nextLine;
                about += "\n" + "\n" + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return about;
    }

    public ArrayList<Beer> getListOfBeer1(Connection connection) {
        String query = "SELECT * FROM beer ORDER BY id";
        int counter = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Beer beer = new Beer(resultSet.getString("name"), resultSet.getString("alc"), resultSet.getString("price"));
                beerList.add(counter,beer);
                counter++;
            }
        } catch (SQLException e) {
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

    public String addBeerToDatabase(Beer beer, Connection connection, String numberOfPage) {
        String query = "UPDATE beer SET name = (?), alc = (?), price = (?) where id = (?)";
        String result = beer.getName() + " " + beer.getVol() + " " + beer.getPrice() + " " + "установлено на кран # " + numberOfPage;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, beer.getName());
            preparedStatement.setString(2, beer.getVol());
            preparedStatement.setString(3, beer.getPrice());

            switch (numberOfPage) {
                case "1":
                    preparedStatement.setInt(4, 1);
                    break;
                case "2":
                    preparedStatement.setInt(4, 2);
                    break;
                case "3":
                    preparedStatement.setInt(4, 3);
                    break;
                case "4":
                    preparedStatement.setInt(4, 4);
                    break;
                case "5":
                    preparedStatement.setInt(4, 5);
                    break;
                case "6":
                    preparedStatement.setInt(4, 6);
                    break;
            }


            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

