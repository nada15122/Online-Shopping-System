public class PremiumCustomer extends Customer {

    public PremiumCustomer(String customerId, String customerName, double balance) {
        super(customerId, customerName, balance);
    }

    @Override
    public double calculateDiscount(double totalAmount) {
        // 15% discount for premium customers
        return totalAmount * 0.15;
    }
}
