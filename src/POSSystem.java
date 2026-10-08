import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class POSSystem {
    private ShoppingCart cart;
    private ProductManager product;
    Connection connection = DatabaseConnection.getConnection();
    DatabaseManager database;



    public POSSystem(ProductManager product, ShoppingCart cart, Connection connection, DatabaseManager database){
        this.product = product;
        this.cart = cart;
        this.connection = connection;
        this.database = database;
    }


    public void checkout(Scanner scanner, ArrayList<CartItem> cartItems){
        if (cartItems.isEmpty()){
            System.out.println("The Cart is Empty.");
            return;
        }

        System.out.print("""
        =================================================================
        ID       Item Name                   Quantity       Price
        =================================================================
        """);
        for (CartItem item : cartItems){
            System.out.printf("%-8s %-27s %-14d %-8.2f\n",
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice()
            );
        }
        System.out.println("=================================================================");
        System.out.printf("Total: RM %.2f%n", cart.calculateTotal());
        System.out.println("=================================================================");

        String confirmation;
        while (true){
            System.out.print("Confirm to check out (y/n): ");
            confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("y") || confirmation.equals("n")){
                break;
            } else {
                System.out.println("Please Enter y for YES or n for NO.");
            }
        }

        if (confirmation.equals("n")){
            return;
        }

        Order newOrder = new Order(database.getNewOrderId(),cartItems, cart.calculateTotal());

        database.insertNewOrder(newOrder);

        for (CartItem item : cartItems) {
            int newQuantity = (item.getProduct().getStock() - item.getQuantity());
            database.updateProduct("stock", newQuantity, item.getProduct().getId());
        }
        cartItems.clear();

        System.out.println("Checkout completed successfully.");
    }



    public void salesSummary(){
        double totalRevenue = database.getOrdersTotal();
        int totalQuantity = database.getOrdersQuantity();
        double averageOrder;

        if (totalRevenue == 0 && totalQuantity == 0){
            averageOrder = 0;
            System.out.println("No order record yet.");
        } else {
            averageOrder = (totalRevenue/totalQuantity);
        }

        System.out.println("========== SALES SUMMARY ==========\n");

        System.out.println("Total Orders: " + totalQuantity);
        System.out.printf("Total Revenue: RM %.2f\n", totalRevenue);
        System.out.printf("Average Order: RM %.2f\n", averageOrder);

        System.out.println("===================================");

    }

}
