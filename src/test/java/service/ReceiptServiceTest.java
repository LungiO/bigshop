package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import products.Item;
import products.ProductType;
import products.Receipt;

import java.util.HashMap;
import java.util.Map;

public class ReceiptServiceTest {

    private ReceiptService receiptService;

    @Before
    public void setup() {
        this.receiptService = ReceiptService.getInstance();
    }

    @Test
    public void createReceipt() {
        Map<ProductType, Item> items = new HashMap<>();
        addItem(items, ProductType.SIM_CARD, 1);
        addItem(items, ProductType.PHONE_CASE, 2);
        addItem(items, ProductType.WIRED_EARPHONES, 1);
        addItem(items, ProductType.WIRELESS_EARPHONES, 1);

        Receipt receipt = receiptService.createReceipt(items);
        Assert.assertEquals(120, receipt.getSubtotal(), 0.001);
        Assert.assertEquals(14.4, receipt.getTax(), 0.001);
        Assert.assertEquals(134.4, receipt.getTotal(), 0.001);
    }

    public void addItem(Map<ProductType, Item> items, ProductType productType, int count) {
        Item item = new Item(productType);
        item.setCount(count);
        items.put(productType, item);
    }
}
