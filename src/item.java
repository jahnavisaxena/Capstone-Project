public class Item {
    private String name;
    private int price;
    private int quantity;
    private String variant;
    private String customOption;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
        this.variant = "";
        this.customOption = "";
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getVariant() {
        return variant;
    }

    public String getCustomOption() {
        return customOption;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setCustomOption(String customOption) {
        this.customOption = customOption;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
