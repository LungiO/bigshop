package products;

public class Receipt {
    private String receipt;
    private double subtotal;
    private double total;
    private double tax;

    public Receipt(String receipt, double subtotal, double total, double tax) {
        this.receipt = receipt;
        this.subtotal = subtotal;
        this.total = total;
        this.tax = tax;
    }

    public String getReceipt() {
        return receipt;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public double getTax() {
        return tax;
    }
}
