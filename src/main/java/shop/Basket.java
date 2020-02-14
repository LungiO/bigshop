package shop;

import products.Item;
import products.ProductType;
import products.Receipt;
import service.DiscountService;
import service.ReceiptService;
import service.ValidationService;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Receipt receipt;
    private Map<ProductType, Item> items;
    private DiscountService discountService;
    private ReceiptService receiptService;
    private ValidationService validationService;

    public Basket() {
        this.items = new HashMap<>();
        this.discountService = DiscountService.getInstance();
        this.receiptService = ReceiptService.getInstance();
        this.validationService = ValidationService.getInstance();
    }

    public void addItem(ProductType productType) {
        if (productType == ProductType.UNKNOWN) {
            return;
        }
        Item item = items.computeIfAbsent(productType, product -> new Item(productType));
        item.incrementCount();
        validationService.validateItems(items);
    }

    public void checkOut() {
        validationService.validateItems(items);
        discountService.calculateDiscounts(items);
        this.receipt = this.receiptService.createReceipt(items);
    }

    public Receipt getReceipt() {
        if (this.receipt == null) {
            return null;
        }
        return this.receipt;
    }
}
