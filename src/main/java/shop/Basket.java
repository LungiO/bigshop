package shop;

import products.Item;
import products.ProductType;
import products.Receipt;
import service.DiscountService;
import service.ReceiptService;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Receipt receipt;
    private Map<ProductType, Item> items;
    private DiscountService discountService;
    private ReceiptService receiptService;

    public Basket() {
        this.items = new HashMap<>();
        this.discountService = DiscountService.getInstance();
        this.receiptService = ReceiptService.getInstance();
    }

    public void addItem(ProductType productType) {
        if (productType == ProductType.UNKNOWN) {
            return;
        }
        Item item = items.computeIfAbsent(productType, product -> new Item(productType));
        item.incrementCount();
    }

    public void checkOut() {
        discountService.calculateDiscounts(items);
        this.receipt = this.receiptService.createReceipt(items);
    }

    public String getReceipt() {
        if (this.receipt == null) {
            return null;
        }
        return this.receipt.getReceipt();
    }
}
