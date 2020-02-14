package products;

public enum Discount {
    INSURANCE_EARPHONES("Insurance + Earphones deal", 0.20),
    SIM_2_FOR_1("2 for 1 discount", 1);

    String desc;
    double discount;

    Discount(String desc, double discount) {
        this.desc = desc;
        this.discount = discount;
    }

    public String getDesc() {
        return desc;
    }

    public double getDiscount() {
        return discount;
    }
}
