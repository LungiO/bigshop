package service;

import products.Item;
import products.ProductType;
import products.Receipt;

import java.util.Map;

public class ReceiptService {
    public static final String LINE_SEPARATOR = "---------------------------------------\n";
    private static ReceiptService instance = null;

    private ReceiptService() {
    }

    public static ReceiptService getInstance() {
        if (instance == null) {
            instance = new ReceiptService();
        }
        return instance;
    }

    public Receipt createReceipt(Map<ProductType, Item> items) {
        StringBuilder sb = new StringBuilder();

        double subTotal = 0;
        double total = 0;
        double tax = 0;

        for (Map.Entry<ProductType, Item> entrySet : items.entrySet()) {
            ProductType productType = entrySet.getKey();
            Item item = entrySet.getValue();

            subTotal += item.getTotalPrice();
            total += item.getTotalPriceWithTax();
            tax += item.getTotalTax();

            addReceiptEntry(sb, productType, item);
            addDiscountEntry(sb, item);
        }

        addSubTotalAndTax(sb, subTotal, tax);
        addTotal(sb, total);

        return new Receipt(sb.toString(), subTotal, total, tax);
    }

    private void addReceiptEntry(StringBuilder sb, ProductType productType, Item item) {
        sb.append(String.format(
                "%dx %-20s %10.2f chf\n", item.getCount(), productType.getDesc(), item.getTotalPriceWithoutDiscount()
        ));
    }

    private void addDiscountEntry(StringBuilder sb, Item item) {
        double totalDiscount = item.getTotalDiscount();
        if (totalDiscount > 0) {
            sb.append(String.format(
                    "   %-20s %10.2f chf\n", "discount", (totalDiscount * -1)
            ));
        }
    }

    private void addSubTotalAndTax(StringBuilder sb, double subTotal, double tax) {
        sb.append(LINE_SEPARATOR);
        sb.append(String.format(
                "%-23s %10.2f chf\n", "subtotal", subTotal
        ));
        sb.append(String.format(
                "%-23s %10.2f chf\n", "tax", tax
        ));
    }

    private void addTotal(StringBuilder sb, double total) {
        sb.append(LINE_SEPARATOR);
        sb.append(String.format(
                "%-23s %10.2f chf\n", "total", total
        ));
    }
}
