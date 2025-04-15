import java.awt.*;
import javax.swing.*;

public class App {

    public static void main(String[] args) {
        showSplashScreen();
    }

    private static void showSplashScreen() {
        JWindow splash = new JWindow();

        JPanel content = new JPanel();
        content.setBackground(new Color(255, 248, 240));
        content.setLayout(new BorderLayout());

        JLabel title = new JLabel("🍽 Cuisine Crossroad", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(153, 51, 0));

        JLabel subtitle = new JLabel("Your gateway to world flavors", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 16));
        subtitle.setForeground(new Color(102, 51, 0));

        content.add(title, BorderLayout.CENTER);
        content.add(subtitle, BorderLayout.SOUTH);

        splash.getContentPane().add(content);
        splash.setSize(450, 200);

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
        loginFrame.setSize(450, 400);
        loginFrame.setLayout(new BorderLayout());

        // --- Top panel with logo ---
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBackground(new Color(255, 248, 240));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        // Load the logo image (Update path as needed)
        try {
            ImageIcon logoIcon = new ImageIcon("logo.png"); // Replace with your logo path
            Image img = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(img));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoPanel.add(logoLabel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("Logo image not found.");
        }

        // --- Welcome text ---
        JLabel welcome = new JLabel("Login to Cuisine Crossroad", SwingConstants.CENTER);
        welcome.setFont(new Font("Georgia", Font.BOLD, 20));
        welcome.setForeground(new Color(102, 51, 0));
        logoPanel.add(welcome, BorderLayout.SOUTH);

        // --- Form panel ---
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

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

            if (username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(loginFrame, "Welcome to Cuisine Crossroad!");
                // Launch dashboard or next page
            } else {
                JOptionPane.showMessageDialog(loginFrame,
                        "Invalid username or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}