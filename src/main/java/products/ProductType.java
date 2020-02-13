package products;

import java.util.Arrays;

public enum ProductType {
    SIM_CARD("SIM card", 20, 0.12),
    PHONE_CASE("Phone case", 10, 0.12),
    PHONE_INSURANCE("Phone insurance", 120, 0),
    WIRED_EARPHONES("Wired earphones", 30, 0.12),
    WIRELESS_EARPHONES("Wireless earphones", 50, 0.12),
    UNKNOWN("unknown", 0, 0);

    private String desc;
    private double price;
    private double tax;

    ProductType(String desc, double price, double tax) {
        this.desc = desc;
        this.price = price;
        this.tax = tax;
    }

    public static ProductType byDescription(String desc) {
        return Arrays.stream(values())
                .filter(productType -> productType.desc.equalsIgnoreCase(desc))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public double getTax() {
        return tax;
    }
}
