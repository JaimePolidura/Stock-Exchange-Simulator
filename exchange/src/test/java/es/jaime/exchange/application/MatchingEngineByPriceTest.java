package es.jaime.exchange.application;

import es.jaime.exchange._shared.ExchangeConfigurationMock;
import es.jaime.exchange._shared.QueuePublisherMock;
import es.jaime.exchange.domain.*;
import org.junit.Before;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static es.jaime.exchange.domain.OrderType.*;

public class MatchingEngineByPriceTest {
    private MatchingEngine matchingEngineTest;
    private TradeProcessor tradeProcessorTest;
    private QueuePublisher queuePublisher;
    private ExchangeConfiguration exchangeConfiguration;

    @Before
    public void setUp(){
        this.exchangeConfiguration = new ExchangeConfigurationMock();
        this.queuePublisher = new QueuePublisherMock();
        this.tradeProcessorTest = new TradeProcessorImpl(this.queuePublisher, exchangeConfiguration);
        this.matchingEngineTest = new MatchingEngineByPrice(this.tradeProcessorTest, exchangeConfiguration);
    }

    private List<Order> createBuyOrders(){
         return List.of(
                createRandomOrder(BUY,10, 13),
                createRandomOrder(BUY,11, 11),
                createRandomOrder(BUY,-1, 2),
                createRandomOrder(BUY,8, 120),
                createRandomOrder(BUY,9, 13),
                createRandomOrder(BUY,-1, 21),
                createRandomOrder(BUY,12, 12),
                createRandomOrder(BUY,9, 12),
                createRandomOrder(BUY,5, 7),
                createRandomOrder(BUY,7, 34)
        );
    }

    private List<Order> createSellOrders(){
        return List.of(
                createRandomOrder(SELL,12, 13),
                createRandomOrder(SELL,11, 11),
                createRandomOrder(SELL,10, 2),
                createRandomOrder(SELL,8, 120),
                createRandomOrder(SELL,11, 13),
                createRandomOrder(SELL,17, 21),
                createRandomOrder(SELL,12, 12),
                createRandomOrder(SELL,-1, 12),
                createRandomOrder(SELL,8, 7),
                createRandomOrder(SELL,9, 34)
        );
    }

    private Order createRandomOrder(OrderType type, double executionPrice, int quantity){
        return new Order(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                LocalDateTime.now().toString(),
                executionPrice,
                quantity,
                this.exchangeConfiguration.getTicker(),
                type
        );
    }
}
