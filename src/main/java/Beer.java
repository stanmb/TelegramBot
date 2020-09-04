import java.util.ArrayList;

public class Beer {
    private String name;
    private String vol;
    private String priceFor05;

    public Beer(String name, String vol, String priceFor05) {
        this.name = name;
        this.vol = vol;
        this.priceFor05 = priceFor05;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }


    public String getPriceFor05() {
        return priceFor05;
    }

    public void setPriceFor05(String priceFor05) {
        this.priceFor05 = priceFor05;
    }
}
