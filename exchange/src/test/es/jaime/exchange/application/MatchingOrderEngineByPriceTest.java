package es.jaime.exchange.application;

import es.jaime.exchange._shared.EventBusMock;
import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.Order;
import es.jaime.exchange.domain.models.orders.SellOrder;
import es.jaime.exchange.domain.services.*;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.jaime.exchange._shared.ExchangeConfigurationMock;
import es.jaime.exchange._shared.QueuePublisherMock;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class MatchingOrderEngineByPriceTest {
    private MatchingOrderEngine matchingEngineTest;
    private TradeProcessor tradeProcessorTest;
    private MessagePublisher queuePublisher;
    private ExchangeConfiguration exchangeConfiguration;
    private ExecutorService executorService;

    @Before
    public void setUp(){
        this.executorService = Executors.newSingleThreadExecutor();

        this.exchangeConfiguration = new ExchangeConfigurationMock();
        this.queuePublisher = new QueuePublisherMock();
        this.tradeProcessorTest = new TradeProcessorImpl(this.queuePublisher, exchangeConfiguration, new MatchingPriceEngineImpl(), new EventBusMock());
        this.matchingEngineTest = new MatchingOrderEngineByPrice(this.tradeProcessorTest, exchangeConfiguration, new MatchingPriceEngineImpl(), messagePublisher, new EventBusMock());

        createBuyOrders().forEach(order -> matchingEngineTest.enqueueBuyOrder(order));
        createSellOrders().forEach(order -> matchingEngineTest.enqueueSellOrder(order));
    }

    @Test
    public void allOrdersInQueue(){
        assertEquals(5, matchingEngineTest.getBuyOrdersQueue().size());
        assertEquals(5, matchingEngineTest.getSellOrdersQueue().size());
    }

    @Test
    public void buyOrdersInCorrectOrderInQueue(){
        double[] expectedBuyOrderByPrice = new double[]{12.0, 10.0, 9.0, 8.0, -1.0};
        double[] actualBuyOrderByPrice = iterateThroughPriorityQueue(this.matchingEngineTest.getBuyOrdersQueue())
                .stream()
                .mapToDouble(Order::getExecutionPrice)
                .toArray();

        Assert.assertArrayEquals(expectedBuyOrderByPrice, actualBuyOrderByPrice, 0);
    }

    @Test
    public void sellOrdersInCorrectOrderInQueue() {
        double[] expectedSellOrderByPrice = new double[]{-1.0, 8.0, 10.0, 11.0, 11.0, };
        double[] actualSellOrderByPrice = iterateThroughPriorityQueue(this.matchingEngineTest.getSellOrdersQueue())
                .stream()
                .mapToDouble(Order::getExecutionPrice)
                .toArray();

        Assert.assertArrayEquals(expectedSellOrderByPrice, actualSellOrderByPrice, 0);
    }

    //First match should be -> sellorder:{ exec.price: -1, quantity: 13} & buyorder: {exec.price: 12, quantity: 12}
    @Test
    public void testFirstMatch(){
        executeOneMatch();

        Queue<Message> executedOrderQueueMessage = getExecutedOrdersQueue();

        Assert.assertEquals(2, executedOrderQueueMessage.size());

        Assert.assertEquals(5, matchingEngineTest.getSellOrdersQueue().size());
        Assert.assertEquals(4, matchingEngineTest.getBuyOrdersQueue().size());
    }
    
    @SneakyThrows
    @Test
    public void testAllMatches(){
        this.startMatchingEngine();
        Thread.sleep(1500);

        Queue<Message> executedOrders = getExecutedOrdersQueue();

        Assert.assertEquals(10, executedOrders.size());
    }

    private List<BuyOrder> createBuyOrders(){
         return List.of(
                createRandomBuyOrder(10,13),
                createRandomBuyOrder(12,12),
                createRandomBuyOrder(-1,2),
                createRandomBuyOrder(8,120),
                createRandomBuyOrder(9,13)
        );
    }

    private BuyOrder createRandomBuyOrder(double executionPrice, int quantity){
        return new BuyOrder(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                "12/12/12", executionPrice, quantity, "AMZN"
        );
    }

    private List<SellOrder> createSellOrders(){
        return List.of(
                createRandomSellOrder(-1, 13),
                createRandomSellOrder(11, 11),
                createRandomSellOrder(10, 2),
                createRandomSellOrder(8, 120),
                createRandomSellOrder(11, 13)
        );
    }

    private SellOrder createRandomSellOrder(double executionPrice, int quantity){
        return new SellOrder(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                "12/12/12", executionPrice, quantity, UUID.randomUUID().toString()
        );
    }

    private Queue<Message> getExecutedOrdersQueue(){
        QueuePublisherMock queuePublisherMock = (QueuePublisherMock) this.queuePublisher;

        return queuePublisherMock.getQueue();
    }

    private List<? extends Order> iterateThroughPriorityQueue(Queue<? extends Order> queue){
        List<Order> result = new ArrayList<>();
        int queueSize = queue.size();

        for (int i = 0; i < queueSize; i++)
            result.add(queue.poll());

        return result;
    }

    @SneakyThrows
    private void executeOneMatch(){
        this.startMatchingEngine();
        Thread.sleep(exchangeConfiguration.matchingEngineSleep() / 2);
        this.stopMatchingEngine();
    }

    private void startMatchingEngine(){
        this.executorService.submit(this.matchingEngineTest);
    }

    private void stopMatchingEngine(){
        this.matchingEngineTest.stop();
    }
}
