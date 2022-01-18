package es.jaime.exchange.application;

import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.domain.models.events.*;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;
import es.jaime.exchange.domain.services.*;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class MatchingOrderEngineByPrice implements MatchingOrderEngine, Runnable {
    //orderIdToBeCancelled -> cancel order
    private final Map<String, CancelOrder> cancelOrders;

    private final Queue<BuyOrder> buyOrders;
    private final Queue<SellOrder> sellOrders;

    private final TradeProcessor tradeProcessor;
    private final ExchangeConfiguration configuration;
    private final MatchingPriceEngine matchingPriceEngine;
    private final OrderCancellationProcessor orderCancellator;
    private final EventBus eventBus;
    private boolean running;

    public MatchingOrderEngineByPrice(TradeProcessor tradeProcessor, ExchangeConfiguration configuration, MatchingPriceEngine matchingPriceEngine,
                                      OrderCancellationProcessor orderCancellator, EventBus eventBus){
        this.tradeProcessor = tradeProcessor;
        this.configuration = configuration;
        this.matchingPriceEngine = matchingPriceEngine;
        this.orderCancellator = orderCancellator;
        this.eventBus = eventBus;
        this.buyOrders = new PriorityBlockingQueue<>();
        this.sellOrders = new PriorityBlockingQueue<>();
        this.cancelOrders = new HashMap<>();
        this.running = true;
    }

    @EventListener({BuyOrderArrived.class})
    public void onNewBuyOrder(BuyOrderArrived buyOrderArrivedEvent) {
        addBuyOrder(buyOrderArrivedEvent.getBuyOrder());
    }

    @EventListener({SellOrderArrived.class})
    public void onNewSellOrder(SellOrderArrived sellOrderArrivedEvent) {
        addSellOrder(sellOrderArrivedEvent.getSellOrder());
    }

    @EventListener({CancelOrderArrived.class})
    public void onNewCancelOrder(CancelOrderArrived event){
        addCancelOrder(event.getCancelOrder());
    }

    @Override
    public void addBuyOrder(BuyOrder order) {
        buyOrders.offer(order);
    }

    @Override
    public void addSellOrder(SellOrder order) {
        sellOrders.offer(order);
    }

    @Override
    public void addCancelOrder(CancelOrder order) {
        this.cancelOrders.put(order.getOrderToCancel(), order);
    }

    @Override
    public void run() {
        while(running) {
            checkForOrdersInQueue();
            waitForOrders();
        }
    }

    private void checkForOrdersInQueue(){
        if(!thereIsTwoOrdersInBuyAndSell()) return;

        BuyOrder buyOrder = buyOrders.poll();
        SellOrder sellOrder = sellOrders.poll();

        var buyOrderCancelled = checkIfOrderIsCancelled(buyOrder);
        var sellOrderCancelled = checkIfOrderIsCancelled(sellOrder);
        var someOfThemAreCancelled = buyOrderCancelled || sellOrderCancelled;

        if(someOfThemAreCancelled) {
            if(!buyOrderCancelled) reenqueueIfSomeOrderWasntAllCompleted(buyOrder, this.buyOrders);
            if(!sellOrderCancelled) reenqueueIfSomeOrderWasntAllCompleted(sellOrder, this.sellOrders);

            return;
        }

        if(matchingPriceEngine.isThereAnyMatch(buyOrder, sellOrder)){
            tradeProcessor.process(buyOrder, sellOrder);

            reenqueueIfSomeOrderWasntAllCompleted(buyOrder, this.buyOrders);
            reenqueueIfSomeOrderWasntAllCompleted(sellOrder, this.sellOrders);
        }else{
            processMismatch(buyOrder, sellOrder);
        }
    }

    @SneakyThrows
    private void waitForOrders(){
        Thread.sleep(configuration.matchingEngineSleep());
    }

    private boolean thereIsTwoOrdersInBuyAndSell(){
        return buyOrders.size() > 0 && sellOrders.size() > 0;
    }

    private boolean checkIfOrderIsCancelled(ExecutionOrder order){
        if(this.cancelOrders.containsKey(order.getOrderId())){
            CancelOrder cancelOrder = this.cancelOrders.remove(order.getOrderId());

            this.orderCancellator.cancel(order, cancelOrder);

            return true;
        }

        return false;
    }

    private <T extends ExecutionOrder> void reenqueueIfSomeOrderWasntAllCompleted(T order, Queue<T> queue){
        if(order.getQuantity() > 0){
            queue.add(order);
        }
    }

    @SneakyThrows
    private void processMismatch(BuyOrder buyOrder, SellOrder sellOrder) {
        boolean ttlExpiredBuyOrder = decreaseTtl(buyOrder);
        boolean ttlExpiredSellOrder = decreaseTtl(sellOrder);

        if(ttlExpiredBuyOrder) eventBus.publish(new ExceptionOccurred(new TtlExpired(buyOrder))); else buyOrders.add(buyOrder);
        if(ttlExpiredSellOrder) eventBus.publish(new ExceptionOccurred(new TtlExpired(sellOrder))); else sellOrders.add(sellOrder);
    }

    private boolean decreaseTtl(ExecutionOrder order){
        int actualTtlOrder = order.decreaseTtlByOne();

        return actualTtlOrder <= 0;
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
    public Map<String, CancelOrder> getCancelOrders() {
        return new HashMap<>(this.cancelOrders);
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public void start() {
        this.running = true;
    }
}
