public class Product {
    private String id;
    private String name;
    private double price;
    private int stock;

    public Product(String id, String name, double price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString(){
        return "[" + this.id + "]  [" + this.name + "]  [" + this.price + "]  [" + this.stock + "]";
    }

    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public int getStock(){
        return this.stock;
    }

}