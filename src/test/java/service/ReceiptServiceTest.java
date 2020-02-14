package service;

import org.junit.Before;
import org.junit.Test;
import products.Item;
import products.ProductType;
import products.Receipt;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ReceiptServiceTest {

    private ReceiptService receiptService;

    @Before
    public void setup() {
        this.receiptService = ReceiptService.getInstance();
    }

    @Test
    public void createReceipt() {
        Map<ProductType, Item> items = new LinkedHashMap<>();
        addItem(items, ProductType.SIM_CARD, 1);
        addItem(items, ProductType.PHONE_CASE, 2);
        addItem(items, ProductType.WIRED_EARPHONES, 1);
        addItem(items, ProductType.WIRELESS_EARPHONES, 1);

        Receipt receipt = receiptService.createReceipt(items);
        assertEquals(120, receipt.getSubtotal(), 0.001);
        assertEquals(14.4, receipt.getTax(), 0.001);
        assertEquals(134.4, receipt.getTotal(), 0.001);

        String receiptString = receipt.getReceipt();
        // item entries
        assertTrue(receiptString.matches("(?s).*1x SIM card\\s+20,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*2x Phone case\\s+20,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*1x Wired earphones\\s+30,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*1x Wireless earphones\\s+50,00 chf.*"));

        // no discounts
        assertFalse(receiptString.contains("2 for 1 discount"));
        assertFalse(receiptString.contains("Insurance + Earphones deal"));

        // subtotal, tax, total
        assertTrue(receiptString.matches("(?s).*subtotal\\s+120,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*tax\\s+14,40 chf.*"));
        assertTrue(receiptString.matches("(?s).*total\\s+134,40 chf.*"));
    }

    @Test
    public void createReceipt_withDiscount() {
        Map<ProductType, Item> items = new LinkedHashMap<>();
        addItem(items, ProductType.SIM_CARD, 2);
        addItem(items, ProductType.PHONE_INSURANCE, 2);
        addItem(items, ProductType.WIRED_EARPHONES, 1);
        addItem(items, ProductType.WIRELESS_EARPHONES, 1);

        DiscountService.getInstance().calculateDiscounts(items);

        Receipt receipt = receiptService.createReceipt(items);
        assertEquals(292, receipt.getSubtotal(), 0.001);
        assertEquals(12, receipt.getTax(), 0.001);
        assertEquals(304, receipt.getTotal(), 0.001);

        String receiptString = receipt.getReceipt();
        // item entries
        assertTrue(receiptString.matches("(?s).*2x SIM card\\s+40,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*2x Phone insurance\\s+240,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*1x Wired earphones\\s+30,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*1x Wireless earphones\\s+50,00 chf.*"));

        // no discounts
        assertTrue(receiptString.matches("(?s).*2 for 1 discount\\s+-20,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*Insurance [+] Earphones deal\\s+-48,00 chf.*"));

        // subtotal, tax, total
        assertTrue(receiptString.matches("(?s).*subtotal\\s+292,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*tax\\s+12,00 chf.*"));
        assertTrue(receiptString.matches("(?s).*total\\s+304,00 chf.*"));
    }

    public void addItem(Map<ProductType, Item> items, ProductType productType, int count) {
        Item item = new Item(productType);
        item.setCount(count);
        items.put(productType, item);
    }
}
