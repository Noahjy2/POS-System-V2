import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Scanner;
import java.sql.Connection;

public class ProductManager {

    Scanner scanner;
    Connection connection;
    DatabaseManager database;

    public ProductManager(Scanner scanner, Connection connection, DatabaseManager database){
        this.scanner = scanner;
        this.connection = connection;
        this.database = database;
    }

    public void addProduct() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        if (database.getProduct("product_id", id) != null) {
            System.out.println("ID Already Exist.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        if (database.getProduct("name", name) != null) {
            System.out.println("Product Already Exist.");
            return;
        }

        double price;
        while (true) {
            try {
                System.out.print("Enter Price: ");
                String input = scanner.nextLine();
                price = Double.parseDouble(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
            }
        }

        int stock;
        while (true) {
            try {
                System.out.print("Enter Stock: ");
                String input = scanner.nextLine();
                stock = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
            }
        }

        database.insertNewProduct(id, name, price, stock);
        System.out.println("New product added successful.");
    }


    //<DONE>
    public void deleteProduct() {

        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        if (database.getProduct("product_id", id) == null) {
            System.out.println("Product Not Found.");
        }

        database.deleteProduct(id);
    }


    //<DONE>
    public void searchProduct() {

        System.out.print("""
                Search Product by?
                1. ID
                2. Name
                3. Price
                4. Stock
                """);
        System.out.print("Enter your choice (1-4): ");
        String choice = scanner.nextLine();

        Product selectedProduct = null;

        switch (choice){
            case "1" -> {
                System.out.print("Enter ID: ");
                String id = scanner.nextLine();
                selectedProduct = database.getProduct("product_id",id);
            }
            case "2" ->{
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                selectedProduct = database.getProduct( "name", name);
            }
            case "3" -> {
                double price;
                while (true) {
                    try {
                        System.out.print("Enter Price: ");
                        String input = scanner.nextLine();
                        price = Double.parseDouble(input);
                        break;
                    } catch (NumberFormatException e){
                        System.out.println("Please enter a valid input.");
                    }
                }
                selectedProduct = database.getProduct("price", price);
            }
            case "4" -> {
                double stock;
                while (true) {
                    try {
                        System.out.print("Enter Stock: ");
                        String input = scanner.nextLine();
                        stock = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException e){
                        System.out.println("Please enter a valid input.");
                    }
                }
                selectedProduct = database.getProduct("stock", stock);
            }
            default -> System.out.println("Invalid choice.");
        }

        if (selectedProduct != null) {
            System.out.println(selectedProduct.toString());
        } else {
            System.out.println("Product Not Found.");
        }
    }


    public void updateProduct() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();

        Product selectedProduct = database.getProduct("product_id",id);

        if (selectedProduct == null) {
            System.out.println("Product Not Found.");
            return;
        }


        System.out.print("""
                1. ID
                2. Name
                3. Price
                4. Stock
                """);
        System.out.print("Enter your choice (1-4): ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1" -> {

                System.out.print("Enter New ID: ");
                String newId = scanner.nextLine();

                database.updateProduct("product_id", newId, id);
            }
            case "2" -> {

                System.out.print("Enter New Name: ");
                String newName = scanner.nextLine();

                database.updateProduct("name", newName, id);
            }
            case "3" -> {

                double newPrice;

                while (true) {
                    System.out.print("Enter New Price: ");
                    String input = scanner.nextLine();

                    try {
                        newPrice = Double.parseDouble(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }

                database.updateProduct("price", newPrice, id);
            }
            case "4" -> {

                int newStock;

                while (true) {
                    System.out.print("Enter New Stock: ");
                    String input = scanner.nextLine();

                    try {
                        newStock = Integer.parseInt(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                }
                database.updateProduct("stock", newStock, id);
            }

            default -> System.out.println("Invalid choice.");

        }
        System.out.println("Updated successful.");
    }
}