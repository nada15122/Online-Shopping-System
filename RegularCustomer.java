public class RegularCustomer extends Customer {

    public RegularCustomer(String customerId, String customerName, double balance) {
        super(customerId, customerName, balance);
    }

    @Override
    public double calculateDiscount(double totalAmount) {
        // 5% discount for regular customers
        return totalAmount * 0.05;
    }
}