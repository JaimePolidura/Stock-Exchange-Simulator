package es.jaime.exchange.application;

import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.ExceptionOccurredEvent;
import es.jaime.exchange.domain.events.OrderArrivedEvent;
import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.models.OrderType;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MatchingOrderEngine;
import es.jaime.exchange.domain.services.MatchingPriceEngine;
import es.jaime.exchange.domain.services.TradeProcessor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class MatchingOrderEngineByPrice implements MatchingOrderEngine, Runnable {
    private final Queue<Order> buyOrders;
    private final Queue<Order> sellOrders;
    private final TradeProcessor tradeProcessor;
    private final ExchangeConfiguration configuration;
    private final MatchingPriceEngine matchingPriceEngine;
    private final EventBus eventBus;
    private boolean running;

    public MatchingOrderEngineByPrice(TradeProcessor tradeProcessor, ExchangeConfiguration configuration,
                                      MatchingPriceEngine matchingPriceEngine, EventBus eventBus){
        this.tradeProcessor = tradeProcessor;
        this.configuration = configuration;
        this.matchingPriceEngine = matchingPriceEngine;
        this.eventBus = eventBus;
        this.buyOrders = new PriorityBlockingQueue<>();
        this.sellOrders = new PriorityBlockingQueue<>();
        this.running = true;
    }

    @EventListener
    public void onNewOrder(OrderArrivedEvent orderArrivedEvent) {
        this.enqueue(orderArrivedEvent.getOrder());
    }

    @Override
    public void enqueue(Order order) {
        if(order.getType() == OrderType.BUY){
            buyOrders.add(order);
        }else{
            sellOrders.add(order);
        }
    }

    @Override
    public void run() {
        while(running) {
            checkForOrdersInQueue();
            waitForOrders();
        }
    }

    @SneakyThrows
    private void waitForOrders(){
        Thread.sleep(configuration.matchingEngineSleep());
    }

    private void checkForOrdersInQueue(){
        if(buyOrders.isEmpty() || sellOrders.isEmpty()) {
            return;
        }

        Order buyOrder = buyOrders.poll();
        Order sellOrder = sellOrders.poll();

        if(matchingPriceEngine.isThereAnyMatch(buyOrder, sellOrder)){
            tradeProcessor.process(buyOrder, sellOrder);

            reenqueueIfSomeOrderWasntAllCompleted(buyOrder, sellOrder);
        }else{
            processMismatch(buyOrder, sellOrder);
        }
    }

    private void reenqueueIfSomeOrderWasntAllCompleted(Order buyOrder, Order sellOrder){
        if(buyOrder.getQuantity() > 0){
            this.buyOrders.add(buyOrder);
        }
        if(sellOrder.getQuantity() > 0){
            this.sellOrders.add(sellOrder);
        }
    }

    @SneakyThrows
    private void processMismatch(Order buyOrder, Order sellOrder) {
        int actualTtlBuyOrder = buyOrder.decreaseTtlByOne();
        int actualTtlSellOrder = sellOrder.decreaseTtlByOne();

        if(actualTtlBuyOrder <= 0)
            eventBus.publish(new ExceptionOccurredEvent(new TtlExpired(buyOrder)));
        if(actualTtlSellOrder <= 0)
            eventBus.publish(new ExceptionOccurredEvent(new TtlExpired(sellOrder)));
    }

    @Override
    public Queue<Order> getBuyOrdersQueue() {
        return new PriorityQueue<>(this.buyOrders);
    }

    @Override
    public Queue<Order> getSellOrdersQueue() {
        return new PriorityQueue<>(this.sellOrders);
    }

    @Override
    public void stop() {
        this.running = false;
    }
}
