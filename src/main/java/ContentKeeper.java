import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContentKeeper {
    // Initialization path to the About file
    File file = new File("C:\\Users\\andre\\Desktop\\about.txt");


    //method gets about information from file and returns it in String variable
    public String getAbout() {
        String about = "";
        String nextLine;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/resources/about")));
            while ((nextLine = reader.readLine()) != null) {
                about += nextLine;
                about += "\n" + "\n" + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return about;
    }

    public ArrayList<Beer> getListOfBeer(Connection connection) {
        ArrayList<Beer> beerList = new ArrayList<Beer>();
        String query = "SELECT * FROM beer ORDER BY id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Beer beer = new Beer(resultSet.getString("title"), resultSet.getString("alc"),
                        resultSet.getString("price"));
                beerList.add(beer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beerList;
    }

    public String addBeerToDatabase(Beer beer, Connection connection, String numberOfPage) {
        String query = "UPDATE beer SET title = (?), alc = (?), price = (?) where id = (?)";
        StringBuilder result = new StringBuilder();
        result.append(beer.getName()).append("\n").append(beer.getVol()).append("\n").append(beer.getPrice()).append("\n")
                .append("Установлено на кран # ").append(numberOfPage);
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
        return result.toString();
    }
    public ArrayList<Snack> getListOfSnacks(Connection connection) {
        String query = "SELECT * FROM snacks ORDER BY id";
        ArrayList<Snack> snackList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Snack snack = new Snack(resultSet.getString("title"), resultSet.getString("weight"),
                        resultSet.getString("price"));
                snackList.add(snack);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return snackList;
    }

    public <T extends  ItemToSell> String getItemsString (ArrayList<T> itemList) {
        StringBuilder nextItem = new StringBuilder();

        for (ItemToSell item : itemList) {
            nextItem.append(item.toString());
        }
        return nextItem.toString();
    }

}

