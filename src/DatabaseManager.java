import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class DatabaseManager {

    Connection connection;

    public DatabaseManager(Connection connection){
        this.connection = connection;
    }

    public void createTable(){
        try {

            String createProductsTableSQL =
                    "CREATE TABLE IF NOT EXISTS products (" +
                    "product_id VARCHAR(20) PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "price DOUBLE(10,2) NOT NULL, " +
                    "stock INT NOT NULL)";

            PreparedStatement createProductsTable = connection.prepareStatement(createProductsTableSQL);
            createProductsTable.executeUpdate();

            String createOrdersTableSQL =
                    "CREATE TABLE IF NOT EXISTS orders(" +
                    "order_id VARCHAR(20) PRIMARY KEY, " +
                    "total DOUBLE(10,2) NOT NULL)";

            PreparedStatement createOrdersTable = connection.prepareStatement(createOrdersTableSQL);
            createOrdersTable.executeUpdate();


            String createOrderItemsTableSQL =
                    "CREATE TABLE IF NOT EXISTS order_items(" +
                    "item_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "order_id VARCHAR(20) NOT NULL, " +
                    "product_id VARCHAR(20) NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "price DOUBLE(10,2) NOT NULL," +
                    "quantity INT NOT NULL)";

            PreparedStatement createOrderItemsTable = connection.prepareStatement(createOrderItemsTableSQL);
            createOrderItemsTable.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void insertNewProduct(String id, String name, double price, int stock){

        String insertProductSQL = "INSERT INTO PRODUCTS (product_id, name, price, stock) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement insertProduct = connection.prepareStatement(insertProductSQL);

            insertProduct.setString(1, id);
            insertProduct.setString(2, name);
            insertProduct.setDouble(3, price);
            insertProduct.setInt(4, stock);

            insertProduct.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getNewOrderId(){

        int newIdValue = 0;

        String getIdSQL =
                "SELECT order_id " +
                "FROM orders " +
                "ORDER BY order_id DESC " +
                "LIMIT 1";

        try {
            PreparedStatement ps = connection.prepareStatement(getIdSQL);
            ResultSet rs = ps.executeQuery();

            if (!(rs.next())) {
                return "Order00001";
            }


            String order_id = rs.getString("order_id");
            String[] value = order_id.split("Order");

            newIdValue = (Integer.parseInt(value[1])) + 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format("Order%05d", newIdValue);
    }


    public void insertNewOrder(Order order){

        String insertOrderSQL = "INSERT INTO orders(order_id, total) VALUES(?, ?)";

        String insertOrderItemsSQL = "INSERT INTO order_items(order_id, product_id, name, price, quantity) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement insertOrder = connection.prepareStatement(insertOrderSQL);
            insertOrder.setString(1, order.getId());
            insertOrder.setDouble(2, order.getTotal());

            insertOrder.executeUpdate();

            for (CartItem item : order.getCart()) {
                PreparedStatement insertOrderItems = connection.prepareStatement(insertOrderItemsSQL);
                insertOrderItems.setString(1, order.getId());
                insertOrderItems.setString(2, item.getProduct().getId());
                insertOrderItems.setString(3, item.getProduct().getName());
                insertOrderItems.setDouble(4, item.getProduct().getPrice());
                insertOrderItems.setInt(5, item.getQuantity());

                insertOrderItems.executeUpdate();
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }



    public Product getProduct(String column, Object value){

        Product product = null;
        String sql = "SELECT * FROM products WHERE " + column + " = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,value);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                String pId = rs.getString("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                product = new Product(pId,name,price,stock);
            } else {
                return null;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return product;
    }


    public double getOrdersTotal(){

        double total = 0;
        String sql = "SELECT total FROM orders";

        boolean hasData = false;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                hasData = true;

                double value = rs.getDouble("total");
                total += value;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return total;
    }

    public int getOrdersQuantity(){

        int totalOrder = 0;
        String sql = "SELECT order_id FROM orders";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while (rs.next()){
                totalOrder += 1;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return  totalOrder;
    }


    public void deleteProduct(String id){
        String sql = "DELETE FROM products WHERE product_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,id);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void updateProduct(String column, Object value, String id){

        String sql = "UPDATE products SET " + column + " = ? WHERE product_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,value);
            ps.setString(2,id);
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void viewAllProduct(){
        String sql =
                "SELECT * FROM products " +
                "ORDER BY product_id";

        boolean hasData = false;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.print("""
                        ---------------------------------------------------------------------
                        ID       | Name                              | Price        | Stock
                        ---------------------------------------------------------------------
                        """);

            while (rs.next()) {
                hasData = true;
                String id = rs.getString("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                System.out.printf("%-8s | %-33s | RM%-10.2f | %-8d %n",
                        id,
                        name,
                        price,
                        stock);
            }

            if (!hasData){
                System.out.println("No Data Exist Yet.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}


