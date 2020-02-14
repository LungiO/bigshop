package products;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int count;
    private double unitPrice;
    private double tax;

    // If I use a map instead of a list, I could group different kind of discounts together.
    // This would also allow me to print the different discounts in the receipt.
    private List<Discount> discounts;

    public Item(ProductType productType) {
        this.unitPrice = productType.getPrice();
        this.tax = productType.getTax();
        this.count = 0;
        this.discounts = new ArrayList<>();
    }

    public void clearDiscounts() {
        this.discounts.clear();
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void incrementCount() {
        this.count++;
    }

    public double getTotalDiscount() {
        // assumption: only one discount per unit allowed, no accumulation of discounts
        return this.discounts.stream()
                .mapToDouble(discounts -> discounts.getDiscount() * this.unitPrice)
                .sum();
    }

    public double getTotalPriceWithoutDiscount() {
        return this.count * this.unitPrice;
    }

    public double getTotalPrice() {
        return getTotalPriceWithoutDiscount() - getTotalDiscount();
    }

    public double getTotalTax() {
        return this.getTotalPrice() * this.tax;
    }

    public double getTotalPriceWithTax() {
        return this.getTotalPrice() * (1 + this.tax);
    }
}
