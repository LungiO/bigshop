package service;

import org.junit.Before;
import org.junit.Test;
import products.Item;
import products.ProductType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DiscountServiceTest {

    private DiscountService discountService;

    @Before
    public void setup() {
        this.discountService = DiscountService.getInstance();
    }

    @Test
    public void calculateDiscounts_simCard() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.SIM_CARD, 10);

        discountService.calculateDiscounts(items);
        assertEquals(100, items.get(ProductType.SIM_CARD).getTotalDiscount(), 0.001);

        // calling calculate twice should first remove existing discounts
        discountService.calculateDiscounts(items);
        assertEquals(100, items.get(ProductType.SIM_CARD).getTotalDiscount(), 0.001);
    }

    @Test
    public void calculateDiscounts_simCard_unevenCount() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.SIM_CARD, 3);

        discountService.calculateDiscounts(items);
        assertEquals(20, items.get(ProductType.SIM_CARD).getTotalDiscount(), 0.001);
    }

    @Test
    public void calculateDiscounts_simCard_noDiscount() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.SIM_CARD, 1);

        discountService.calculateDiscounts(items);
        assertEquals(0, items.get(ProductType.SIM_CARD).getTotalDiscount(), 0.001);
    }

    @Test
    public void calculateDiscounts_phoneInsurance() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.PHONE_INSURANCE, 10);
        addItem(items, ProductType.WIRED_EARPHONES, 2);

        discountService.calculateDiscounts(items);
        assertEquals(48, items.get(ProductType.PHONE_INSURANCE).getTotalDiscount(), 0.001);
    }

    @Test
    public void calculateDiscounts_phoneInsurance_moreEarphones() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.PHONE_INSURANCE, 1);
        addItem(items, ProductType.WIRED_EARPHONES, 2);

        discountService.calculateDiscounts(items);
        assertEquals(24, items.get(ProductType.PHONE_INSURANCE).getTotalDiscount(), 0.001);
    }

    @Test
    public void calculateDiscounts_phoneInsurance_bothEarphonesTypes() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.PHONE_INSURANCE, 10);
        addItem(items, ProductType.WIRED_EARPHONES, 2);
        addItem(items, ProductType.WIRELESS_EARPHONES, 1);

        discountService.calculateDiscounts(items);
        assertEquals(72, items.get(ProductType.PHONE_INSURANCE).getTotalDiscount(), 0.001);
    }

    @Test
    public void calculateDiscounts_phoneInsurance_noDiscount() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.PHONE_INSURANCE, 10);

        discountService.calculateDiscounts(items);
        assertEquals(0, items.get(ProductType.PHONE_INSURANCE).getTotalDiscount(), 0.001);
    }

    public void addItem(Map<ProductType, Item> items, ProductType productType, int count) {
        Item item = new Item(productType);
        item.setCount(count);
        items.put(productType, item);
    }
}
