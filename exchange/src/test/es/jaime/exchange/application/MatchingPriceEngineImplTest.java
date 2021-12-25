package es.jaime.exchange.application;

import es.jaime.exchange.application.MatchingPriceEngineImpl;
import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.models.OrderType;
import es.jaime.exchange.domain.services.MatchingPriceEngine;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue(priceEngine.isThereAnyMatch(createOrder(10), createOrder(10)));

        //Buy price > sell price -> true
        assertTrue(priceEngine.isThereAnyMatch(createOrder(15), createOrder(10)));

        //Buy market sell limit -> true
        assertTrue(priceEngine.isThereAnyMatch(createOrder(-1), createOrder(10)));

        //Buy limit sell market -> true
        assertTrue(priceEngine.isThereAnyMatch(createOrder(10), createOrder(-1)));

        //Both market -> false
        assertFalse(priceEngine.isThereAnyMatch(createOrder(-1), createOrder(-1)));

        //Buy price < sell price -> false
        assertFalse(priceEngine.isThereAnyMatch(createOrder(10), createOrder(15)));
    }

    @Test
    public void getPriceMatch(){
        //Same price -> 10
        assertEquals(10, priceEngine.getPriceMatch(createOrder(10), createOrder(10)), 1);

        //Buy price > sell price -> buy price
        assertEquals(15, priceEngine.getPriceMatch(createOrder(15), createOrder(10)), 1);

        //Buy market sell limit -> sell price
        assertEquals(10, priceEngine.getPriceMatch(createOrder(-1), createOrder(10)), 1);

        //Buy limit sell market -> true
        assertEquals(10, priceEngine.getPriceMatch(createOrder(10), createOrder(-1)), 1);
    }

    private Order createOrder(double price){
        return new Order("a", "a", "a", price, 1, "AMZN", OrderType.BUY);
    }
}
