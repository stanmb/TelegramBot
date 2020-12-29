public class Beer extends ItemToSell {
    private String vol;

    public Beer(String name, String vol, String price) {
        super.name = name;
        super.price = price;
        this.vol = vol;
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
