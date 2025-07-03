import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JFrame {

    public AdminPanel() {
        setTitle("Cuisine Crossroad - Admin Panel");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("📋 Order History Management", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("Order ID");
        model.addColumn("Customer Name");
        model.addColumn("Item");
        model.addColumn("Quantity");
        model.addColumn("Total Price");
        model.addColumn("Order Receiving Time");

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/login", "root", "Parth@123"
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("item"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("order_receiving_time")
                });
            }

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
