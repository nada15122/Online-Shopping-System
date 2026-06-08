import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Product> productList = new ArrayList<>();
    private static Customer currentCustomer = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;
        do {
            System.out.println("==================================================");
            System.out.println("       WELCOME TO ONLINE SHOPPING SYSTEM         ");
            System.out.println("          Developed by: Nada Nabil               ");
            System.out.println("==================================================");
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Search Product");
            System.out.println("4. Add Product To Cart");
            System.out.println("5. Remove Product From Cart");
            System.out.println("6. View Shopping Cart");
            System.out.println("7. Purchase Products");
            System.out.println("8. Add Balance");
            System.out.println("9. View Balance");
            System.out.println("10. Generate Invoice");
            System.out.println("11. Display Order History");
            System.out.println("12. Customer Information");
            System.out.println("13. Apply Discount");
            System.out.println("14. Exit System");
            System.out.print("Enter Your Choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayProducts();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    if (checkCustomer()) addProductToCart();
                    break;
                case 5:
                    if (checkCustomer()) removeProductFromCart();
                    break;
                case 6:
                    if (checkCustomer()) viewCart();
                    break;
                case 7:
                    if (checkCustomer()) purchaseProducts();
                    break;
                case 8:
                    if (checkCustomer()) addBalance();
                    break;
                case 9:
                    if (checkCustomer()) viewBalance();
                    break;
                case 10:
                    if (checkCustomer()) generateInvoice();
                    break;
                case 11:
                    if (checkCustomer()) viewOrderHistory();
                    break;
                case 12:
                    if (checkCustomer()) customerInformation();
                    break;
                case 13:
                    if (checkCustomer()) applyDiscount();
                    break;
                case 14:
                    System.out.println("Exiting System. Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 14);
    }

    private static boolean checkCustomer() {
        if (currentCustomer == null) {
            System.out.println("\n[!] You must initialize a customer profile first to perform this action.");
            initializeCustomer();
            return currentCustomer != null;
        }
        return true;
    }

    private static void initializeCustomer() {
        System.out.println("\n=== Welcome to the Shopping System Initialization ===");
        System.out.print("Enter Customer ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Select Type (1 for Regular, 2 for Premium): ");
        int type = scanner.nextInt();
        scanner.nextLine();

        if (type == 2) {
            currentCustomer = new PremiumCustomer(id, name, balance);
        } else {
            currentCustomer = new RegularCustomer(id, name, balance);
        }
        System.out.println("Customer Profile Created Successfully!\n");
    }

    private static void addProduct() {
        System.out.print("Enter Product ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();

        productList.add(new Product(id, name, price, qty));
        System.out.println("Product Added Successfully.");
    }

    private static void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\n--- Available Products ---");
        for (Product p : productList) {
            p.displayProduct();
        }
    }

    private static void searchProduct() {
        System.out.print("Enter Product Name to Search: ");
        String name = scanner.nextLine();
        boolean found = false;
        for (Product p : productList) {
            if (p.getProductName().equalsIgnoreCase(name)) {
                p.displayProduct();
                found = true;
            }
        }
        if (!found) System.out.println("Product not found.");
    }

    private static void addProductToCart() {
        displayProducts();
        System.out.print("Enter Product ID to add to cart: ");
        String id = scanner.nextLine();
        Product selectedProduct = null;
        for (Product p : productList) {
            if (p.getProductId().equals(id)) {
                selectedProduct = p;
                break;
            }
        }

        if (selectedProduct == null) {
            System.out.println("Invalid Product ID.");
            return;
        }

        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();

        if (qty > selectedProduct.getQuantity()) {
            System.out.println("Insufficient stock! Only " + selectedProduct.getQuantity() + " available.");
        } else {
            currentCustomer.getCart().addToCart(selectedProduct, qty);
            selectedProduct.updateQuantity(-qty);
            System.out.println("Added to cart.");
        }
    }

    private static void removeProductFromCart() {
        currentCustomer.getCart().viewCart();
        System.out.print("Enter Product ID to remove from cart: ");
        String id = scanner.nextLine();

        for (Product item : currentCustomer.getCart().getCartItems()) {
            if (item.getProductId().equalsIgnoreCase(id)) {
                for (Product p : productList) {
                    if (p.getProductId().equals(id)) {
                        p.updateQuantity(item.getQuantity());
                    }
                }
            }
        }
        currentCustomer.getCart().removeFromCart(id);
    }

    private static void viewCart() {
        currentCustomer.getCart().viewCart();
    }

    private static void purchaseProducts() {
        Cart cart = currentCustomer.getCart();
        if (cart.getCartItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        double total = cart.calculateTotal();
        double discount = currentCustomer.calculateDiscount(total);
        double finalPrice = total - discount;

        System.out.println("Total Amount: $" + total);
        System.out.println("Discount: $" + discount);
        System.out.println("Final Price to Pay: $" + finalPrice);

        if (currentCustomer.getBalance() >= finalPrice) {
            currentCustomer.setBalance(currentCustomer.getBalance() - finalPrice);
            Order newOrder = new Order(cart.getCartItems(), total, discount);
            currentCustomer.addOrderToHistory(newOrder);
            cart.clearCart();
            System.out.println("Purchase Successful! Order placed.");
            newOrder.displayInvoice();
        } else {
            System.out.println("Insufficient balance! Please add funds.");
        }
    }

    private static void addBalance() {
        System.out.print("Enter Amount to Add: ");
        double amount = scanner.nextDouble();
        currentCustomer.addBalance(amount);
    }

    private static void viewBalance() {
        System.out.println("Your current balance is: $" + currentCustomer.getBalance());
    }

    private static void generateInvoice() {
        ArrayList<Order> history = currentCustomer.getOrderHistory();
        if (history.isEmpty()) {
            System.out.println("No recent completed purchases to generate invoice for.");
        } else {
            history.get(history.size() - 1).displayInvoice();
        }
    }

    private static void viewOrderHistory() {
        ArrayList<Order> history = currentCustomer.getOrderHistory();
        if (history.isEmpty()) {
            System.out.println("No history found.");
            return;
        }
        System.out.println("--- Order History ---");
        for (Order o : history) {
            o.displayInvoice();
        }
    }

    private static void customerInformation() {
        currentCustomer.displayCustomerInfo();
    }

    private static void applyDiscount() {
        double total = currentCustomer.getCart().calculateTotal();
        double discount = currentCustomer.calculateDiscount(total);
        System.out.println("Based on your account type (" + currentCustomer.getClass().getSimpleName() + "):");
        System.out.println("Current Cart Value: $" + total);
        System.out.println("Potential Discount: $" + discount);
    }
}
