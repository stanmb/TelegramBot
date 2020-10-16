public class Snack extends ItemToSell {
    String weight;

    public Snack(String name, String weight,String price ) {
        super.name = name;
        super.price = price;
        this.weight = weight;
    }

    public String getName() {
        return super.name;
    }

    public String getWeight() {
        return weight;
    }

    public String getPrice() {
        return super.price;
    }

    public String toString() {
        return  this.getName() + "\n" + this.getWeight() + " грамм" + "\n" + this.getPrice() + "₽" + "\n" + "\n" +"\n";

    }
}
