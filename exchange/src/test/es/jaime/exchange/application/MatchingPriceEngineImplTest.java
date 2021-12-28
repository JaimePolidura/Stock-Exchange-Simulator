package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.Order;
import es.jaime.exchange.domain.models.OrderType;
import es.jaime.exchange.domain.models.orders.SellOrder;
import es.jaime.exchange.domain.services.MatchingPriceEngine;
import org.junit.Before;
import org.junit.Test;

import static es.jaime.exchange.domain.models.OrderType.*;
import static org.junit.Assert.*;

public class MatchingPriceEngineImplTest {
    private MatchingPriceEngine priceEngine;

    @Before
    public void setUp(){
        this.priceEngine = new MatchingPriceEngineImpl();
    }

    @Test
    public void isThereAnyMatch(){
        //Same price -> true
        assertTrue(priceEngine.isThereAnyMatch(createBuyOrder(10), createSellOrder(10)));

        //Buy price > sell price -> true
        assertTrue(priceEngine.isThereAnyMatch(createBuyOrder(15), createSellOrder(10)));

        //Buy market sell limit -> true
        assertTrue(priceEngine.isThereAnyMatch(createBuyOrder(-1), createSellOrder(10)));

        //Buy limit sell market -> true
        assertTrue(priceEngine.isThereAnyMatch(createBuyOrder(10), createSellOrder(-1)));

        //Both market -> false
        assertFalse(priceEngine.isThereAnyMatch(createBuyOrder(-1), createSellOrder(-1)));

        //Buy price < sell price -> false
        assertFalse(priceEngine.isThereAnyMatch(createBuyOrder(10), createSellOrder(15)));
    }

    @Test
    public void getPriceMatch(){
        //Same price -> 10
        assertEquals(10, priceEngine.getPriceMatch(createBuyOrder(10), createSellOrder(10)), 1);

        //Buy price > sell price -> buy price
        assertEquals(15, priceEngine.getPriceMatch(createBuyOrder(15), createSellOrder(10)), 1);

        //Buy market sell limit -> sell price
        assertEquals(10, priceEngine.getPriceMatch(createBuyOrder(-1), createSellOrder(10)), 1);

        //Buy limit sell market -> true
        assertEquals(10, priceEngine.getPriceMatch(createBuyOrder(10), createSellOrder(-1)), 1);
    }

    private SellOrder createSellOrder(double price){
        return new SellOrder("a", "a", "a", price, 1, SELL, "a");
    }

    private BuyOrder createBuyOrder(double price){
        return new BuyOrder("a", "a", "a", price, 1,BUY, "a");
    }
}
