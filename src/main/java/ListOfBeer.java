import java.awt.geom.Area;
import java.io.*;
import java.util.ArrayList;

public class ListOfBeer {
    public  ArrayList<Beer> allBeers = new ArrayList<Beer>();

    public String getAbout() {
        return about;
    }

    private String about = "Крафт-бар Hoppy на Бородинском проезде в Клину ежедневно открывает свои двери для истинных ценителей и знатоков пенного напитка.\n" +
            "\n" +
            "Здесь гармонично сочетаются качественное обслуживание, богатый ассортимент, красивая музыка, теплая душевная атмосфера, располагающая к неспешной дружеской беседе.\n" +
            "\n" +
            "В баре подают только крафтовое пиво, приготовленное по традиционной технологии на небольших пивоварнях. Здесь работают с поставщиками из России, Италии, Англии, Германии, США и других государств. Линейка сортов поражает своим разнообразием. Бармены не просто предложат попробовать новинку, но расскажут много интересного об ингредиентах, способе производства, особенностях.";
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
        return allBeers;
    }

    public void addBear(String lineToPars) {
        String[] line = lineToPars.split("/");
        Beer beer = new Beer(line[0],line[1],line[2]);
        allBeers.add(beer);
    }
}
