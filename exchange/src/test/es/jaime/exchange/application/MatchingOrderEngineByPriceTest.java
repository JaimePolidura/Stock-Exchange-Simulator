package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.models.OrderType;
import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.services.*;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import es.jaime.exchange._shared.ExchangeConfigurationMock;
import es.jaime.exchange._shared.QueuePublisherMock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static es.jaime.exchange.domain.models.OrderType.BUY;
import static es.jaime.exchange.domain.models.OrderType.SELL;
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
        this.tradeProcessorTest = new TradeProcessorImpl(this.queuePublisher, exchangeConfiguration, new MatchingPriceEngineImpl());
        this.matchingEngineTest = new MatchingOrderEngineByPrice(this.tradeProcessorTest, exchangeConfiguration, new MatchingPriceEngineImpl());

        createBuyOrders().forEach(order -> matchingEngineTest.enqueue(order));
        createSellOrders().forEach(order -> matchingEngineTest.enqueue(order));
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

    private List<Order> createBuyOrders(){
         return List.of(
                createRandomOrder(BUY,10, 13),
                createRandomOrder(BUY,12, 12),
                createRandomOrder(BUY,-1, 2),
                createRandomOrder(BUY,8, 120),
                createRandomOrder(BUY,9, 13)
        );
    }

    private List<Order> createSellOrders(){
        return List.of(
                createRandomOrder(SELL,-1, 13),
                createRandomOrder(SELL,11, 11),
                createRandomOrder(SELL,10, 2),
                createRandomOrder(SELL,8, 120),
                createRandomOrder(SELL,11, 13)
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

    private Queue<Message> getExecutedOrdersQueue(){
        QueuePublisherMock queuePublisherMock = (QueuePublisherMock) this.queuePublisher;

        return queuePublisherMock.getQueue();
    }

    private List<Order> iterateThroughPriorityQueue(Queue<Order> queue){
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
