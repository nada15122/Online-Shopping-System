import java.util.ArrayList;

public abstract class Customer {
    private String customerId;
    private String customerName;
    private double balance;
    private Cart cart;
    private ArrayList<Order> orderHistory;

    // Constructor
    public Customer(String customerId, String customerName, double balance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.balance = balance;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public double getBalance() { return balance; }
    public Cart getCart() { return cart; }
    public ArrayList<Order> getOrderHistory() { return orderHistory; }

    public void setBalance(double balance) { this.balance = balance; }

    // Methods
    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Successfully added $" + amount + " to balance.");
        } else {
            System.out.println("Invalid amount!");
        }
    }

    public void displayCustomerInfo() {
        System.out.println("=== Customer Info ===");
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + customerName);
        System.out.println("Current Balance: $" + balance);
        System.out.println("Customer Type: " + this.getClass().getSimpleName());
    }

    public void addOrderToHistory(Order order) {
        orderHistory.add(order);
    }

    // Abstract method for Polymorphism
    public abstract double calculateDiscount(double totalAmount);
}
