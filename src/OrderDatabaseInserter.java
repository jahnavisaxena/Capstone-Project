import java.sql.*;
import java.util.List;

public class OrderDatabaseInserter {

    public static void insertOrderData(List<Item> globalCart, String customerName, String orderTime) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "Parth@123");

            if (conn != null) {

                String query = "INSERT INTO orders (customer_name, item, quantity, total_price, order_receiving_time) VALUES (?, ?, ?, ?, ?)";

                for (Item item : globalCart) {
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, customerName);
                        stmt.setString(2, item.getName());
                        stmt.setInt(3, item.getQuantity());
                        stmt.setDouble(4, item.getPrice() * item.getQuantity());
                        stmt.setString(5, orderTime);  
                        stmt.executeUpdate();

                        System.out.println("Inserted: " + item.getName());
                    } catch (SQLException ex) {
                        System.out.println("Error inserting item: " + item.getName());
                        ex.printStackTrace();
                    }
                }

                conn.close();
                System.out.println("Order insertion completed!");

            } else {
                System.out.println("Failed to connect to the database.");
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Error during database operation:");
            ex.printStackTrace();
        }
    }
}
