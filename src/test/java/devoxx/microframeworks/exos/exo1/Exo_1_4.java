package devoxx.microframeworks.exos.exo1;

import devoxx.microframeworks.exos.models.Stock;
import devoxx.microframeworks.exos.services.Services;
import devoxx.microframeworks.exos.services.StockService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class Exo_1_4 {

    private StockService service;
    private static final Logger LOG = LoggerFactory.getLogger(Exo_1_4.class);

    @Before
    public void init() {
        service = Services.INSTANCE.get(StockService.class);
    }

    @After
    public void clean() {
        this.service = null;
    }

    @Test
    public void testFindByWine() throws Exception {
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";
        Stock stock;

        stock = service.findByWine(wid);
        LOG.info("Result : {}", stock);
        assertNotNull("A wine should have a stock", stock);
        assertTrue("A wine price is > 0", stock.getPrice() > 0);
        assertTrue("A wine stock is >= 0", stock.getStock() >= 0);
    }

    @Test
    public void testCreateOrder() throws Exception {
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";
        try {
            String result = service.createOrder(wid, 1);
            LOG.info("Result : {}", result);
            assertNotNull("A user can create a result", result);
            assertEquals("The order should be accepted", "Order accepted", result);
        } catch (IllegalArgumentException e) {
            assertEquals("Or the order cannot be completed if stock is empty", "Not enough stock", e.getMessage());
        }
    }
}

