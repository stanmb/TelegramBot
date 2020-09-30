public class Beer {
    private String name;
    private String vol;
    private String price;

    public Beer(String name, String vol, String price) {
        this.name = name;
        this.vol = vol;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getVol() {
        return vol;
    }

    public String getPrice() {
        return price;
    }

    public String toString() {
        return  this.getName() + "\n" + this.getVol() + "\n" + this.getPrice() + "₽" + "\n" + "\n" +"\n";
    }

}
