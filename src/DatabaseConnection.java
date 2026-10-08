import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection(){

        String url = "jdbc:mysql://localhost:3306/pos_system";
        String username = "root";
        String password = "your_password";
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            return connection;
        } catch (SQLException e){
            System.out.println("Cannot connect to database.");
            e.printStackTrace();
            return null;
        }
    }
}
