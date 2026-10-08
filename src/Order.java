import java.util.ArrayList;

public class Order {
    private String orderId;
    private ArrayList<CartItem> cart;
    private double total;

    public Order(String orderId, ArrayList<CartItem> cart, double total) {
        this.orderId = orderId;
        this.cart = new ArrayList<>(cart);
        this.total = total;
    }

    public String getId(){
        return this.orderId;
    }
    public ArrayList<CartItem> getCart(){
        return this.cart;
    }
    public double getTotal(){
        return this.total;
    }


    public void setId(String orderId){
        this.orderId = orderId;
    }
    public void setTotal(double total){
        this.total = total;
    }


}