package service;

import org.junit.Before;
import org.junit.Test;
import products.Item;
import products.ProductType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ValidationServiceTest {

    private ValidationService validationService;

    @Before
    public void setup() {
        this.validationService = ValidationService.getInstance();
    }

    @Test
    public void validateItems_simCard() {
        Map<ProductType, Item> items = new HashMap<>();
        Item simCard = new Item(ProductType.SIM_CARD);
        simCard.setCount(2);

        validationService.validateItems(items);
        assertEquals(2, simCard.getCount());
    }

    @Test
    public void validateItems_simCard_greater10() {
        Map<ProductType, Item> items = new HashMap<>();
        Item simCard = new Item(ProductType.SIM_CARD);
        simCard.setCount(12);
        items.put(ProductType.SIM_CARD, simCard);

        validationService.validateItems(items);
        assertEquals(10, simCard.getCount());
    }
}
