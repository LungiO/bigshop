package shop;

import org.junit.Before;
import org.junit.Test;
import products.ProductType;
import products.Receipt;

import static org.junit.Assert.assertEquals;

public class BasketTest {

    private Basket basket;

    @Before
    public void setup() {
        this.basket = new Basket();
    }

    @Test
    public void checkout_empty() {
        this.basket.checkOut();
        Receipt receipt = this.basket.getReceipt();

        assertEquals(0, receipt.getSubtotal(), 0.001);
        assertEquals(0, receipt.getTax(), 0.001);
        assertEquals(0, receipt.getTotal(), 0.001);
    }

    @Test
    public void checkout() {
        this.basket.addItem(ProductType.WIRELESS_EARPHONES);
        this.basket.checkOut();
        Receipt receipt = this.basket.getReceipt();

        assertEquals(50, receipt.getSubtotal(), 0.001);
        assertEquals(6, receipt.getTax(), 0.001);
        assertEquals(56, receipt.getTotal(), 0.001);
    }

    @Test
    public void checkout_validation() {
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.addItem(ProductType.SIM_CARD);
        this.basket.checkOut();
        Receipt receipt = this.basket.getReceipt();

        // max 10 SIM cards
        assertEquals(100, receipt.getSubtotal(), 0.001);
        assertEquals(12, receipt.getTax(), 0.001);
        assertEquals(112, receipt.getTotal(), 0.001);
    }
}
