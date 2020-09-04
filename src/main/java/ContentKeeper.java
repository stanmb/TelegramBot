import java.io.*;
import java.util.ArrayList;

public class ContentKeeper {
    //all beer list initialization
    public  ArrayList<Beer> beerList = new ArrayList<Beer>();

    //method returns a list of all beer
    public ArrayList<Beer> getListOfBeer() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\andre\\Desktop\\list.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                addBear(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beerList;
    }

    //method parses line from file with beer, makes new object Beer and add it to the list of all beer
    public void addBear(String lineToPars) {
        String[] line = lineToPars.split("/");
        Beer beer = new Beer(line[0],line[1],line[2]);
        beerList.add(beer);
    }
    //method gets about information from file and returns it in String variable
    public String getAbout() {
        String about = "";
        String nextLine;
        try  {
            BufferedReader reader = new BufferedReader( new FileReader("C:\\Users\\andre\\Desktop\\about.txt"));
            while((nextLine = reader.readLine()) != null) {
                about += nextLine;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return about;
    }
}
