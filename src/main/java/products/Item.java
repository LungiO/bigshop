package products;

import java.util.HashMap;
import java.util.Map;

public class Item {
    private int count;
    private double unitPrice;
    private double tax;

    private Map<Discount, Integer> discounts;

    public Item(ProductType productType) {
        this.unitPrice = productType.getPrice();
        this.tax = productType.getTax();
        this.count = 0;
        this.discounts = new HashMap<>();
    }

    public void clearDiscounts() {
        this.discounts.clear();
    }

    public void addDiscount(Discount discount, int count) {
        int discountCount = this.discounts.computeIfAbsent(discount, d -> 0);
        discountCount += count;
        this.discounts.put(discount, discountCount);
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
        double totalDiscount = 0;
        for (Map.Entry<Discount, Integer> entry : this.discounts.entrySet()) {
            totalDiscount += entry.getKey().getDiscount() * this.unitPrice * entry.getValue();
        }
        return totalDiscount;
    }

    public Map<Discount, Double> getDiscountsGrouped() {
        Map<Discount, Double> discountsGrouped = new HashMap<>();
        for (Map.Entry<Discount, Integer> entry : this.discounts.entrySet()) {
            Discount discount = entry.getKey();
            double discountValue = discount.getDiscount() * this.unitPrice * entry.getValue();
            discountsGrouped.put(discount, discountValue);
        }
        return discountsGrouped;
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
