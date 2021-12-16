package es.jaime.exchange.application;

import es.jaime.exchange.domain.*;
import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.infrastructure.OrderArrivedEvent;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class MatchingEngineByPrice implements MatchingEngine, Runnable {
    private final Queue<Order> buyOrders;
    private final Queue<Order> sellOrders;
    private final TradeProcessor tradeProcessor;
    private final ExchangeConfiguration configuration;
    private boolean running;

    public MatchingEngineByPrice(TradeProcessor tradeProcessor, ExchangeConfiguration configuration){
        this.tradeProcessor = tradeProcessor;
        this.configuration = configuration;
        this.buyOrders = new PriorityBlockingQueue<>();
        this.sellOrders = new PriorityBlockingQueue<>();
        this.running = true;
    }

    @EventListener
    public void onNewOrder(OrderArrivedEvent orderArrivedEvent) {
        this.enqueue(orderArrivedEvent.getOrder());
    }

    @Override
    public synchronized void enqueue(Order order) {
        if(order.getType() == OrderType.BUY){
            buyOrders.add(order);
        }else{
            sellOrders.add(order);
        }
    }

    @Override
    public synchronized void run() {
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

        if(isThereAnyMatch(buyOrder, sellOrder)){
            tradeProcessor.process(buyOrder, sellOrder);

            reenqueueIfSomeOrderWasntAllCompleted(buyOrder, sellOrder);
        }else{
            processMismatch(buyOrder, sellOrder);
        }
    }

    private boolean isThereAnyMatch(Order buyOrder, Order sellOrder) {
        return (buyOrder.getExecutionPrice() >= sellOrder.getExecutionPrice()) &&
                (!(buyOrder.getExecutionPrice() == -1) || !(sellOrder.getExecutionPrice() == -1));
    }

    private void reenqueueIfSomeOrderWasntAllCompleted(Order buyOrder, Order sellOrder){
        if(buyOrder.getQuantity() > 0){
            this.buyOrders.add(buyOrder);
        }
        if(sellOrder.getQuantity() > 0){
            this.sellOrders.add(sellOrder);
        }
    }

    private void processMismatch(Order buyOrder, Order sellOrder) {
        int actualTtlBuyOrder = buyOrder.decreaseTtlByOne();
        int actualTtlSellOrder = sellOrder.decreaseTtlByOne();

        if(actualTtlBuyOrder <= 0){
            throw new TtlExpired(buyOrder);
        }
        if(actualTtlSellOrder <= 0){
            throw new TtlExpired(sellOrder);
        }
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
