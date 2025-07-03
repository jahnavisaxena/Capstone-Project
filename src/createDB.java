import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class createDB {

    public static void main(String[] args) {
        showSplashScreen();
    }

    private static void showSplashScreen() {
        JWindow splash = new JWindow();

        JPanel content = new JPanel();
        content.setBackground(new Color(255, 248, 240));
        content.setLayout(new BorderLayout());

        JLabel title = new JLabel("🍽 Cuisine Crossroad", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(new Color(153, 51, 0));

        JLabel subtitle = new JLabel("Your gateway to world flavors", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 20));
        subtitle.setForeground(new Color(102, 51, 0));

        content.add(title, BorderLayout.CENTER);
        content.add(subtitle, BorderLayout.SOUTH);

        splash.getContentPane().add(content);
        splash.setSize(450, 450);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        splash.setLocation((screen.width - splash.getSize().width) / 2,
                (screen.height - splash.getSize().height) / 2);

        splash.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splash.setVisible(false);
        splash.dispose();

        showLoginForm();
    }

    private static void showLoginForm() {
        JFrame loginFrame = new JFrame("Cuisine Crossroad - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 500);
        loginFrame.setLayout(new BorderLayout());

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(244, 237, 225));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        try {
            ImageIcon logoIcon = new ImageIcon("image\\Cuisine Crossroad.jpg");
            Image img = logoIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(img));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoPanel.add(logoLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Logo image not found.");
        }

        JLabel welcome = new JLabel("Login to Cuisine Crossroad", SwingConstants.CENTER);
        welcome.setFont(new Font("Georgia", Font.BOLD, 20));
        welcome.setForeground(new Color(102, 51, 0));
        logoPanel.add(welcome, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel userLabel = new JLabel("SAP_ID:");
        JTextField userField = new JTextField();
        userLabel.setFont(new Font("Georgia",Font.BOLD,20));
        
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        passLabel.setFont(new Font("Georgia",Font.BOLD,20));


        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(153, 51, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        formPanel.add(userLabel);
        formPanel.add(userField);
        formPanel.add(passLabel);
        formPanel.add(passField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        loginFrame.add(logoPanel, BorderLayout.NORTH);
        loginFrame.add(formPanel, BorderLayout.CENTER);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.equals("admin") && password.equals("123admin")) {
                JOptionPane.showMessageDialog(loginFrame, "✅ Admin Login Successful!");
                loginFrame.dispose();
                new AdminPanel(); 
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/login",
                        "root", "Parth@123"
                );

                String query = "SELECT * FROM credentials WHERE Sap_id=? AND Password=?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(loginFrame, "✅ Login Successful!");
                    loginFrame.dispose();
                    new MainMenu();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "❌ Invalid username or password");
                }

                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(loginFrame, "Database Error: " + ex.getMessage());
            }
        });
    }
}
