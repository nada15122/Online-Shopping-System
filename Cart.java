import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public void addToCart(Product product, int qty) {
        // Check if item already exists in cart to update qty, or add as new
        for (Product item : cartItems) {
            if (item.getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + qty);
                return;
            }
        }
        // Create a copy of product with the selected cart quantity
        cartItems.add(new Product(product.getProductId(), product.getProductName(), product.getPrice(), qty));
    }

    public void removeFromCart(String productId) {
        Product toRemove = null;
        for (Product item : cartItems) {
            if (item.getProductId().equalsIgnoreCase(productId)) {
                toRemove = item;
                break;
            }
        }
        if (toRemove != null) {
            cartItems.remove(toRemove);
            System.out.println("Product removed from cart.");
        } else {
            System.out.println("Product not found in cart!");
        }
    }

    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("--- Shopping Cart ---");
        for (Product item : cartItems) {
            System.out.println("ID: " + item.getProductId() + " | Name: " + item.getProductName() +
                    " | Price: $" + item.getPrice() + " | Qty: " + item.getQuantity());
        }
        System.out.println("Total before discount: $" + calculateTotal());
    }

    public double calculateTotal() {
        double total = 0;
        for (Product item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
