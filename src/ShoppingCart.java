import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart{

    Scanner scanner;
    Connection connection;
    ArrayList<CartItem> cartItems;
    DatabaseManager database;

    public ShoppingCart(Scanner scanner, Connection connection, DatabaseManager database, ArrayList<CartItem> cartItems){
        this.scanner = scanner;
        this.connection = connection;
        this.cartItems = cartItems;
        this.database = database;
    }

    public CartItem getCartItem(String id){
        for (CartItem cartItem : cartItems){
            if (cartItem.getProduct().getId().equals(id)){
                return cartItem;
            }
        }

        return null;
    }


    public void addItem(){

        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();

        Product addProduct = database.getProduct("product_id", id);

        if (addProduct == null){
            System.out.println("Product Not Found.");
            return;
        }

        CartItem existingCartItem = getCartItem(id);
        int cartItemQuantity = 0;
        if (existingCartItem != null){
            cartItemQuantity = existingCartItem.getQuantity();
        }

        int addItemQuantity;

        while (true) {
            System.out.print("Enter Quantity: ");
            String input = scanner.nextLine();

            try {
                addItemQuantity = Integer.parseInt(input);
                if (addItemQuantity <= 0){
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }
                break;
            } catch(NumberFormatException e){
                System.out.println("Please Enter Valid Number.");
            }
        }

        if (addItemQuantity + cartItemQuantity > addProduct.getStock()){
            System.out.println(
                    "Not Enough items. Only " +
                    (addProduct.getStock() - cartItemQuantity) +
                    " items left.");
            return;
        }

        if (existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + addItemQuantity);
        } else {
            CartItem cartItem = new CartItem(addProduct, addItemQuantity);
            cartItems.add(cartItem);
        }

        if (addItemQuantity > 1){
            System.out.println("Products Added to Cart Successfully.");
        } else {
            System.out.println("Product Added to Cart Successfully.");
        }
    }



    public void removeItem(){
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();

        CartItem selectedCartItem = getCartItem(id);

        if (selectedCartItem == null){
            System.out.println("Product Not Found in Cart.");
            return;
        }

        cartItems.remove(selectedCartItem);
        System.out.println("Product Removed from Cart Successfully.");
    }


    public void updateQuantity(){
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();

        CartItem selectedCartItem = getCartItem(id);

        if (selectedCartItem == null){
            System.out.println("Product Not Found in Cart.");
            return;
        }

        int newQuantity;
        while (true){
            System.out.print("Enter new quantity: ");
            String input = scanner.nextLine();

            try {
                newQuantity = Integer.parseInt(input);
                if (newQuantity <= 0){
                    System.out.println("Quantity must be greater than 0.");
                    continue;
                }
                break;
            } catch (NumberFormatException e){
                System.out.println("Please Enter Valid Number.");
            }
        }

        if (newQuantity > selectedCartItem.getProduct().getStock()){
            System.out.println("Not enough item. Cart contain " + selectedCartItem.getQuantity() + " of " +
                    selectedCartItem.getProduct().getStock() + " " + selectedCartItem.getProduct().getName() + " stock.");
            return;
        }

        selectedCartItem.setQuantity(newQuantity);
        System.out.println("Quantity Updated Successfully.");
    }


    public void viewCart(){

        if (cartItems.isEmpty()){
            System.out.println("Cart is Empty.");
            return;
        }

        System.out.print("""
        \n=============================================
        ID       Product Name        Quantity
        =============================================
        """);
        for (CartItem cartItem : cartItems){
            System.out.printf("%-8s %-20s %5d\n",
                    cartItem.getProduct().getId(),
                    cartItem.getProduct().getName(),
                    cartItem.getQuantity()
            );
        }
    }

    public double calculateTotal(){
        double totalPrice = 0;

        for (CartItem cartItem : cartItems){
            totalPrice += (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }

        return totalPrice;
    }

}