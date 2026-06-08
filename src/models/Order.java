package src.models;
import java.util.ArrayList;

public class Order {
    private static int idCounter = 1000;
    private String orderId;
    private ArrayList<Product> purchasedItems;
    private double originalTotal;
    private double discountAmount;
    private double finalTotal;

    public Order(ArrayList<Product> cartItems, double originalTotal, double discountAmount) {
        this.orderId = "ORD" + (++idCounter);
        this.purchasedItems = new ArrayList<>(cartItems); // Copy items
        this.originalTotal = originalTotal;
        this.discountAmount = discountAmount;
        this.finalTotal = originalTotal - discountAmount;
    }

    public void displayInvoice() {
        System.out.println("\n=================================");
        System.out.println("         INVOICE - " + orderId);
        System.out.println("=================================");
        for (Product item : purchasedItems) {
            System.out.printf("%-15s x%-3d  $%6.2f\n", item.getProductName(), item.getQuantity(), (item.getPrice() * item.getQuantity()));
        }
        System.out.println("---------------------------------");
        System.out.printf("Subtotal:            $%6.2f\n", originalTotal);
        System.out.printf("Discount Applied:   -$%6.2f\n", discountAmount);
        System.out.printf("Total Paid:          $%6.2f\n", finalTotal);
        System.out.println("=================================\n");
    }
}
