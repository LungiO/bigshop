package service;

import products.Discount;
import products.Item;
import products.ProductType;

import java.util.Map;

public class DiscountService {
    private static DiscountService instance = null;

    private DiscountService() {
    }

    public static DiscountService getInstance() {
        if (instance == null) {
            instance = new DiscountService();
        }
        return instance;
    }

    public void calculateDiscounts(Map<ProductType, Item> items) {
        // clear discounts to prevent double discount calculation if this method is called twice
        clearDiscounts(items);

        addSimCardDiscount(items);
        addPhoneInsuranceDiscount(items);
    }

    private void clearDiscounts(Map<ProductType, Item> items) {
        items.forEach((productType, item) -> {
            item.clearDiscounts();
        });
    }

    private void addSimCardDiscount(Map<ProductType, Item> items) {
        Item simCard = items.get(ProductType.SIM_CARD);
        if (simCard == null) {
            return;
        }

        int discountCount = simCard.getCount() / 2;
        addDiscount(simCard, Discount.SIM_2_FOR_1, discountCount);
    }

    private void addPhoneInsuranceDiscount(Map<ProductType, Item> items) {
        Item phoneInsurance = items.get(ProductType.PHONE_INSURANCE);
        if (phoneInsurance == null) {
            return;
        }

        // assumption: get discount for each earphone bought ->
        // 3x insurances and 2x earphones will give discount twice
        // 1x insurances and 2x earphones will give discount once
        int earPhoneCount = getEarphoneCount(items);
        int discountCount = Math.min(phoneInsurance.getCount(), earPhoneCount);
        addDiscount(phoneInsurance, Discount.INSURANCE_EARPHONES, discountCount);
    }

    private int getEarphoneCount(Map<ProductType, Item> items) {
        int earPhoneCount = 0;
        Item wiredEarPhone = items.get(ProductType.WIRED_EARPHONES);
        Item wirelessEarPhone = items.get(ProductType.WIRELESS_EARPHONES);
        earPhoneCount += wiredEarPhone != null ? wiredEarPhone.getCount() : 0;
        earPhoneCount += wirelessEarPhone != null ? wirelessEarPhone.getCount() : 0;

        return earPhoneCount;
    }

    private void addDiscount(Item product, Discount discount, int count) {
        for (int i = 0; i < count; i++) {
            product.addDiscount(discount);
        }
    }
}
