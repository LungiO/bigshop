package products;

public enum Discount {
    INSURANCE_EARPHONES(0.20),
    SIM_2_FOR_1(1);

    double discount;

    Discount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
