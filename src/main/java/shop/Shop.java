package shop;

import products.ProductType;

public class Shop {

    public static void main(String[] args) {
        Basket basket = new Basket();

        for (String product : args) {
            ProductType productType = ProductType.byDescription(removeTrailingComma(product));
            basket.addItem(productType);
        }

        basket.checkOut();
        System.out.println(basket.getReceipt());
    }

    private static String removeTrailingComma(String product) {
        return product.replaceAll(",$", "");
    }
}
