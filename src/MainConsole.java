import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

//NEW PROBLEM: TRANSACTION IN CHECKOUT NEED IMPROVEMENT FOR STOCK MANAGEMENT AFTER CHECKOUT

public class MainConsole {
    public static void main(String[] args){

        Connection connection = DatabaseConnection.getConnection();
        DatabaseManager database = new DatabaseManager(connection);

        if (connection != null){
            System.out.println("Database connected successfully.");
        }
        database.createTable();

        Scanner scanner = new Scanner(System.in);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        ProductManager product = new ProductManager(scanner,connection,database);
        ShoppingCart cart = new ShoppingCart(scanner,connection,database, cartItems);
        POSSystem pos = new POSSystem(product,cart,connection,database);


        while (true) {
            System.out.print("""
            \n========== POS SYSTEM ==========

            1. Product Management
            2. Shopping Cart
            3. Checkout
            4. Sales Summary
            5. Exit
            """);
            System.out.print("Enter your choice (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> productManagement(scanner, product, database);
                case "2" -> shoppingCart(scanner, cart, cartItems);
                case "3" -> pos.checkout(scanner, cartItems);
                case "4" -> pos.salesSummary();
                case "5" -> System.out.println("Bye");
                default -> System.out.println("Invalid choice.");
            }

            if (choice.equals("5")){
                break;
            }
        }

    }

    public static void productManagement(Scanner scanner, ProductManager manager, DatabaseManager database){

        while (true){
            System.out.print("""
            \n========== PRODUCT MANAGEMENT ==========
            
            1. Add Product
            2. View All Product
            3. Search Product
            4. Update Product
            5. Delete Product
            6. Return Main Menu
            """);
            System.out.print("Enter your choice (1-6): ");
            String option = scanner.nextLine();

            switch (option){
                case "1" -> manager.addProduct();
                case "2" -> database.viewAllProduct();
                case "3" -> manager.searchProduct();
                case "4" -> manager.updateProduct();
                case "5" -> manager.deleteProduct();
                case "6" -> {System.out.println("Returning to Main Menu.");return;}
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void shoppingCart(Scanner scanner, ShoppingCart cart, ArrayList<CartItem> cartItems){


        while (true){
            System.out.print("""
             \n========== SHOPPING CART ==========
            
             1. Add Product to Cart
             2. View Cart
             3. Update Quantity
             4. Remove Product
             5. Clear Cart
             6. Return Main Menu
            """);
            System.out.print("Enter your choice (1-6): ");
            String option = scanner.nextLine();

            switch (option) {
                case "1" -> cart.addItem();
                case "2" -> cart.viewCart();
                case "3" -> cart.updateQuantity();
                case "4" -> cart.removeItem();
                case "5" -> {
                    cartItems.clear();
                    System.out.println("Cart is cleared.");
                }
                case "6" -> {System.out.println("Returning to Main Menu.");return;}
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}