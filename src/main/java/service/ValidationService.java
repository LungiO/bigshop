package service;

import products.Item;
import products.ProductType;

import java.util.Map;

public class ValidationService {
    public static final int MAX_SIM_CARDS = 10;
    private static ValidationService instance;

    private ValidationService() {
    }

    public static ValidationService getInstance() {
        if (instance == null) {
            instance = new ValidationService();
        }
        return instance;
    }

    public void validateItems(Map<ProductType, Item> items) {
        validateSimCardCount(items);
    }

    private void validateSimCardCount(Map<ProductType, Item> items) {
        Item simCard = items.get(ProductType.SIM_CARD);
        if (simCard == null) {
            return;
        } else if (simCard.getCount() > MAX_SIM_CARDS) {
            simCard.setCount(MAX_SIM_CARDS);
            System.err.println(String.format("Max number of %s is %d.\n", ProductType.SIM_CARD.getDesc(), MAX_SIM_CARDS));
        }
    }
}
