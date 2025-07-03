import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class CheckoutPage {

    public CheckoutPage(ArrayList<Item> cart, String deliveryTime) {
        JFrame frame = new JFrame("Cuisine Crossroad - Checkout");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());
    
        JTextArea billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setEditable(false);
    
        int total = 0;
        StringBuilder bill = new StringBuilder();
        bill.append("        🍽 Cuisine Crossroad\n\n");
        bill.append("----------------------------------------\n");
    
        for (Item item : cart) {
            int itemQuantity = item.getQuantity();
            int itemTotal = item.getPrice() * itemQuantity; 
    
            if (itemQuantity > 0) {
                bill.append(String.format("%-25s x%2d = ₹%d\n", item.getName(), itemQuantity, itemTotal));
    
                if (item.getVariant() != null && !item.getVariant().isEmpty()) {
                    bill.append(String.format("Variant: %-20s\n", item.getVariant()));
                }
                if (item.getCustomOption() != null && !item.getCustomOption().isEmpty()) {
                    bill.append(String.format("Topping/Flavor: %-15s\n", item.getCustomOption()));
                }
    
                bill.append("----------------------------------------\n");
    
                total += itemTotal;  
            }
        }
    
        // Final summary
        bill.append(String.format("Total Bill: ₹%d\n", total));
        bill.append(String.format("Delivery Time: %s\n", deliveryTime));
        bill.append("----------------------------------------\n");
        bill.append("Thank you for ordering! 🙏\n");
        bill.append("NOTE: Please receive the order on time to enjoy fresh and hot food!\n");
        bill.append("Plese take the screenshot of this bill and pay the amount at counter to receive order\n");
    
        billArea.setText(bill.toString());
    
        JButton confirmButton = new JButton("Confirm Order");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 18));
        confirmButton.setBackground(Color.GREEN);
    
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "🎉 Order Confirmed for " + deliveryTime + "!\nThank you!");
            frame.dispose();
            System.exit(0);
        });
    
        frame.add(new JScrollPane(billArea), BorderLayout.CENTER);
        frame.add(confirmButton, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
