package products;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ItemTest {

    @Test
    public void getTotalDiscount() {
        Item item = new Item(ProductType.SIM_CARD);
        item.setCount(10);
        item.addDiscount(Discount.SIM_2_FOR_1, 2);

        assertEquals(40, item.getTotalDiscount(), 0.001);
    }

    @Test
    public void getTotalDiscount_noDiscounts() {
        Item item = new Item(ProductType.SIM_CARD);
        item.incrementCount();

        assertEquals(0, item.getTotalDiscount(), 0.001);
    }

    @Test
    public void getDiscountGrouped() {
        Item item = new Item(ProductType.SIM_CARD);
        item.setCount(10);

        item.addDiscount(Discount.SIM_2_FOR_1, 5);
        item.addDiscount(Discount.INSURANCE_EARPHONES, 1);
        Map<Discount, Double> discountsGrouped = item.getDiscountsGrouped();

        Double discountSim = discountsGrouped.get(Discount.SIM_2_FOR_1);
        Double discountEarphone = discountsGrouped.get(Discount.INSURANCE_EARPHONES);
        assertEquals(100, discountSim, 0.001);
        assertEquals(4, discountEarphone, 0.001);
        assertEquals(2, discountsGrouped.size());
    }

    @Test
    public void getTotalPriceWithoutDiscount() {
        Item item = new Item(ProductType.SIM_CARD);
        item.setCount(3);
        item.addDiscount(Discount.SIM_2_FOR_1, 1);

        assertEquals(60, item.getTotalPriceWithoutDiscount(), 0.001);
    }

    @Test
    public void getTotalPrice() {
        Item item = new Item(ProductType.SIM_CARD);
        item.setCount(3);
        item.addDiscount(Discount.SIM_2_FOR_1, 1);

        assertEquals(40, item.getTotalPrice(), 0.001);
    }

    @Test
    public void getTotalPriceWithTax() {
        Item item = new Item(ProductType.SIM_CARD);
        item.setCount(3);
        item.addDiscount(Discount.SIM_2_FOR_1, 1);

        assertEquals(44.8, item.getTotalPriceWithTax(), 0.001);
    }

    @Test
    public void getTotalPriceWithTax_withDiscount() {
        Item item = new Item(ProductType.SIM_CARD);
        item.setCount(2);
        item.addDiscount(Discount.SIM_2_FOR_1, 1);

        assertEquals(22.4, item.getTotalPriceWithTax(), 0.001);
    }

    @Test
    public void getTotalPriceWithTax_noTax() {
        Item item = new Item(ProductType.PHONE_INSURANCE);
        item.setCount(2);

        assertEquals(240, item.getTotalPriceWithTax(), 0.001);
    }

    @Test
    public void getTotalPriceWithTax_noTax_withDiscount() {
        Item item = new Item(ProductType.PHONE_INSURANCE);
        item.setCount(2);
        item.addDiscount(Discount.INSURANCE_EARPHONES, 2);

        assertEquals(192, item.getTotalPriceWithTax(), 0.001);
    }
}
