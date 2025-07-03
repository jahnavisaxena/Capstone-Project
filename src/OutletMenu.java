import java.awt.*;
import java.util.*;
import javax.swing.*;

public class OutletMenu {
    private static ArrayList<Item> globalCart = new ArrayList<>();
    private JFrame frame;

    public OutletMenu(String outletName) {
        frame = new JFrame("Cuisine Crossroad - " + outletName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(950, 750);
        frame.setLayout(new BorderLayout());

        JPanel foodPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        foodPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(foodPanel);

        HashMap<String, Item[]> outletItems = getOutletItems();
        Item[] items = outletItems.getOrDefault(outletName, new Item[0]);

        for (Item baseItem : items) {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            panel.setBackground(Color.WHITE);
            panel.setMaximumSize(new Dimension(850, 180));

            JLabel imageLabel;
            try {
                ImageIcon icon = new ImageIcon("images/" + outletName.toLowerCase().replace(" ", "") + "/" + baseItem.getName().toLowerCase().replace(" ", "") + ".jpg");
                Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
                imageLabel = new JLabel(new ImageIcon(img));
            } catch (Exception e) {
                imageLabel = new JLabel("Image not found");
            }
            panel.add(imageLabel, BorderLayout.WEST);

            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel(baseItem.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
            rightPanel.add(nameLabel);
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            rightPanel.add(new JLabel("Choose Variant:"));
            String[] variants = getVariants(baseItem.getName());
            JComboBox<String> variantBox = new JComboBox<>(variants);
            rightPanel.add(variantBox);

            HashMap<String, Integer> variantPrices = getVariantPrices(baseItem.getName(), baseItem.getPrice());
            JLabel priceLabel = new JLabel("Price: ₹" + variantPrices.get(variants[0]));
            rightPanel.add(priceLabel);

            variantBox.addActionListener(e -> {
                String selectedVariant = (String) variantBox.getSelectedItem();
                priceLabel.setText("Price: ₹" + variantPrices.getOrDefault(selectedVariant, baseItem.getPrice()));
            });

            JComboBox<String> optionsBox = null;
            if (baseItem.getName().equalsIgnoreCase("Pizza") || baseItem.getName().equalsIgnoreCase("Pastry")) {
                String[] options = baseItem.getName().equalsIgnoreCase("Pizza")
                        ? new String[]{"No Topping (₹0)", "Cheese (₹30)", "Extra Cheese (₹50)", "Paneer (₹40)", "Corn (₹25)"}
                        : new String[]{"Vanilla (₹0)", "Chocolate (₹10)", "Strawberry (₹10)"};
                optionsBox = new JComboBox<>(options);
                rightPanel.add(new JLabel(baseItem.getName().equalsIgnoreCase("Pizza") ? "Toppings:" : "Flavors:"));
                rightPanel.add(optionsBox);
                panel.putClientProperty("customOptionBox", optionsBox);
            }

            JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            qtyPanel.setBackground(Color.WHITE);
            qtyPanel.add(new JLabel("Quantity:"));
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            qtyPanel.add(spinner);
            rightPanel.add(qtyPanel);

            panel.putClientProperty("item", baseItem);
            panel.putClientProperty("variantBox", variantBox);
            panel.putClientProperty("variantPrices", variantPrices);
            panel.putClientProperty("quantitySpinner", spinner);

            foodPanel.add(panel);
            panel.add(rightPanel, BorderLayout.CENTER);
        }

        JButton backBtn = new JButton("\u2190 Back to Outlets");
        backBtn.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        JButton addToCartBtn = new JButton("Add to Cart");
        addToCartBtn.addActionListener(e -> {
            for (Component comp : foodPanel.getComponents()) {
                JPanel p = (JPanel) comp;
                Item base = (Item) p.getClientProperty("item");
                String variant = ((JComboBox<String>) p.getClientProperty("variantBox")).getSelectedItem().toString();
                int qty = (int) ((JSpinner) p.getClientProperty("quantitySpinner")).getValue();
                HashMap<String, Integer> variantPrices = (HashMap<String, Integer>) p.getClientProperty("variantPrices");

                int basePrice = variantPrices.getOrDefault(variant, base.getPrice());
                int extraPrice = 0;
                String custom = "";

                if (p.getClientProperty("customOptionBox") != null) {
                    JComboBox<String> box = (JComboBox<String>) p.getClientProperty("customOptionBox");
                    custom = box.getSelectedItem().toString();
                    extraPrice = extractPrice(custom);
                }

                if (qty > 0) {
                    String name = base.getName() + " - " + variant + (custom.isEmpty() ? "" : " [" + custom + "]");
                    Item ordered = new Item(name, basePrice + extraPrice);
                    ordered.setQuantity(qty);
                    globalCart.add(ordered);
                }
            }

            JOptionPane.showMessageDialog(frame, "Items added to cart!");
        });

        JButton checkoutBtn = new JButton("Confirm Order");
        checkoutBtn.addActionListener(e -> {
            if (globalCart.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Cart is empty!");
                return;
            }

            String time = JOptionPane.showInputDialog(frame, "Enter the date and time, when you will receive the order?");
            if (time != null && !time.trim().isEmpty()) {
                String customerName = JOptionPane.showInputDialog(frame, "Enter your name:");

                if (customerName != null && !customerName.trim().isEmpty()) {
                    OrderDatabaseInserter.insertOrderData(globalCart, customerName, time);
                    JOptionPane.showMessageDialog(frame, "Order placed successfully!");
                    new CheckoutPage(globalCart, time);
                    globalCart.clear();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty!");
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backBtn);
        bottomPanel.add(addToCartBtn);
        bottomPanel.add(checkoutBtn);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private HashMap<String, Item[]> getOutletItems() {
        HashMap<String, Item[]> map = new HashMap<>();
        map.put("Dominos", new Item[]{
            new Item("Pizza", 250), new Item("Pasta", 200), new Item("Garlic Bread", 150),
            new Item("Dessert",99)
        });
        map.put("Bakery", new Item[]{
            new Item("Pastry", 100), new Item("Cake", 400), new Item("Cookies", 80),
            new Item("Brownie", 120), new Item("Muffin", 90)
        });
        map.put("Kathi Rolls", new Item[]{
            new Item("Veg Roll", 100), new Item("Non Veg Roll", 130), new Item("Paneer Roll", 150),
            new Item("Egg Roll", 110), new Item("Cheese Roll", 140)
        });
        map.put("Dunkin Donuts", new Item[]{
            new Item("Honey Donut", 80), new Item("Chocolate Donut", 110), new Item("Glazed Donut", 90),
            new Item("Strawberry Donut", 100), new Item("Blueberry Donut", 100)
        });
        map.put("Annapurna", new Item[]{
            new Item("Dosa", 60), new Item("Idli", 40), new Item("Vada", 45),
            new Item("Uttapam", 70), new Item("Lemon Rice", 50)
        });
        map.put("Food Court", new Item[]{
            new Item("Samosa", 30), new Item("Paratha", 40), new Item("Chole Bhature", 70),
            new Item("Pav Bhaji", 60), new Item("Rajma Chawal", 65)
        });
        return map;
    }

    private String[] getVariants(String itemName) {
        switch (itemName.toLowerCase()) {
            case "pizza": return new String[]{"Cheese Pizza", "Veg Pizza", "Paneer Pizza"};
            case "pastry": return new String[]{"Small", "Medium", "Large"};
            case "cake": return new String[]{"1/2 Kg", "1 Kg", "2 Kg"};
            case "paratha": return new String[]{"Aalo","Panner","Onion"};
            case "dessert": return new String[]{"Choco lava cake" , "Mousse cake"};
            default: return new String[]{"Regular"};
        }
    }

    private HashMap<String, Integer> getVariantPrices(String itemName, int basePrice) {
        HashMap<String, Integer> map = new HashMap<>();
        switch (itemName.toLowerCase()) {
            case "pizza":
                map.put("Cheese Pizza", 250);
                map.put("Veg Pizza", 270);
                map.put("Paneer Pizza", 300);
                break;
            case "pastry":
                map.put("Small", 100);
                map.put("Medium", 150);
                map.put("Large", 200);
                break;
            case "cake":
                map.put("1/2 Kg", 400);
                map.put("1 Kg", 700);
                map.put("2 Kg", 1300);
                break;
            case "paratha":
                map.put("Aalo",40);
                map.put("Panner",70);
                map.put("Onion",50);
                break;
            case "dessert":
                map.put("Choco lava cake",99);
                map.put("Mousse cake",110);
                break;
            default:
                map.put("Regular", basePrice);
        }
        return map;
    }

    private int extractPrice(String optionText) {
        if (optionText.contains("\u20B9")) {
            try {
                return Integer.parseInt(optionText.replaceAll(".*\u20B9", "").replaceAll("[^\\d]", ""));
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
