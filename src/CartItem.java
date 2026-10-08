public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return String.format("%s | RM %.2f | Qty: %d | Subtotal: RM %.2f",
                product.getName(),
                product.getPrice(),
                this.quantity,
                this.quantity * product.getPrice()
        );
    }

    public Product getProduct(){
        return this.product;
    }
    public int getQuantity(){
        return this.quantity;
    }


    public void setProduct(Product product){
        this.product = product;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}