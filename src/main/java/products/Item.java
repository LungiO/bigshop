package products;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private int count;
    private double unitPrice;
    private double tax;

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

    public int getCount() {
        return this.count;
    }

    public void incrementCount() {
        this.count++;
    }

    public double getUnitPrice() {
        return this.unitPrice;
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
