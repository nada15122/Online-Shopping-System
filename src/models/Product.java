package src.models;
public class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;

    // Constructor
    public Product(String productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Methods
    public void displayProduct() {
        System.out.println("ID: " + productId + " | Name: " + productName + " | Price: $" + price + " | Quantity: " + quantity);
    }

    public void updateQuantity(int amount) {
        this.quantity += amount;
    }
}
