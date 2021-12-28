package es.jaime.exchange.application;

import es.jaime.exchange.domain.events.*;
import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;
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
    private final Queue<BuyOrder> buyOrders;
    private final Queue<SellOrder> sellOrders;
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

    @EventListener({BuyOrderArrivedEvent.class})
    public void onNewBuyOrder(BuyOrderArrivedEvent buyOrderArrivedEvent) {
        enqueueBuyOrder(buyOrderArrivedEvent.getBuyOrder());
    }

    @Override
    public void enqueueBuyOrder(BuyOrder order) {
        buyOrders.offer(order);
    }

    @EventListener({SellOrderArrivedEvent.class})
    public void onNewSellOrder(SellOrderArrivedEvent sellOrderArrivedEvent) {
        enqueueSellOrder(sellOrderArrivedEvent.getSellOrder());
    }

    @Override
    public void enqueueSellOrder(SellOrder order) {
        sellOrders.offer(order);
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

        BuyOrder buyOrder = buyOrders.poll();
        SellOrder sellOrder = sellOrders.poll();

        if(matchingPriceEngine.isThereAnyMatch(buyOrder, sellOrder)){
            tradeProcessor.process(buyOrder, sellOrder);

            reenqueueIfSomeOrderWasntAllCompleted(buyOrder, sellOrder);
        }else{
            processMismatch(buyOrder, sellOrder);
        }
    }

    private void reenqueueIfSomeOrderWasntAllCompleted(BuyOrder buyOrder, SellOrder sellOrder){
        if(buyOrder.getQuantity() > 0){
            this.buyOrders.add(buyOrder);
        }
        if(sellOrder.getQuantity() > 0){
            this.sellOrders.add(sellOrder);
        }
    }

    @SneakyThrows
    private void processMismatch(BuyOrder buyOrder, SellOrder sellOrder) {
        int actualTtlBuyOrder = buyOrder.decreaseTtlByOne();
        int actualTtlSellOrder = sellOrder.decreaseTtlByOne();

        if(actualTtlBuyOrder <= 0)
            eventBus.publish(new ExceptionOccurredEvent(new TtlExpired(buyOrder)));
        if(actualTtlSellOrder <= 0)
            eventBus.publish(new ExceptionOccurredEvent(new TtlExpired(sellOrder)));
    }

    @Override
    public Queue<BuyOrder> getBuyOrdersQueue() {
        return new PriorityQueue<>(this.buyOrders);
    }

    @Override
    public Queue<SellOrder> getSellOrdersQueue() {
        return new PriorityQueue<>(this.sellOrders);
    }

    @Override
    public void stop() {
        this.running = false;
    }
}
