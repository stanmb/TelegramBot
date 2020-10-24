public class Snack extends ItemToSell {
    String weight;

    public Snack(String name,String price ) {
        super.name = name;
        super.price = price;
    }

    public String getName() {
        return super.name;
    }


    public String getPrice() {
        return super.price;
    }

    public String toString() {
        return  this.getName() + "\n" + this.getPrice() + "₽" + "\n" + "\n" +"\n";

    }
}
