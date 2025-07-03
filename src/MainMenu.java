
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class MainMenu {

    public MainMenu() {
        JFrame frame = new JFrame("Cuisine Crossroad - Choose Outlet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(2, 3, 20, 20)); 

        String[] outlets = {"Dominos", "Dunkin Donuts", "Kathi Rolls", "Annapurna", "Food Court", "Bakery"};
        String[] imagePaths = {
            "image/Dominos.jpg",
            "image/Dunkin.jpg",
            "image/Kathi Roll.jpg",
            "image/Annpurna.jpg",
            "image/Food Court.jpg",
            "image/Bakery.jpg"
        };

        for (int i = 0; i < outlets.length; i++) {
            String outlet = outlets[i];
            String imagePath = imagePaths[i];

            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);

            // Load image
            JLabel imageLabel;
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                ImageIcon icon = new ImageIcon(imagePath);
                Image scaled = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(scaled));
            } else {
                imageLabel = new JLabel("Image not found", SwingConstants.CENTER);
            }

            JButton button = new JButton(outlet);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(153, 51, 0));
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> {
                new OutletMenu(outlet); 
                frame.dispose();
            });

            panel.add(imageLabel, BorderLayout.CENTER);
            panel.add(button, BorderLayout.SOUTH);
            frame.add(panel);
        }

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
