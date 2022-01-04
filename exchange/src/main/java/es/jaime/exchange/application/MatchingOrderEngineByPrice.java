package es.jaime.exchange.application;

import es.jaime.exchange.domain.events.*;
import es.jaime.exchange.domain.exceptions.TtlExpired;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.Order;
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

    @EventListener({BuyOrderArrivedEvent.class})
    public void onNewBuyOrder(BuyOrderArrivedEvent buyOrderArrivedEvent) {
        enqueueBuyOrder(buyOrderArrivedEvent.getBuyOrder());
    }

    @EventListener({SellOrderArrivedEvent.class})
    public void onNewSellOrder(SellOrderArrivedEvent sellOrderArrivedEvent) {
        enqueueSellOrder(sellOrderArrivedEvent.getSellOrder());
    }

    @EventListener({CancelOrderArrivedEvent.class})
    public void onNewCancelOrder(CancelOrderArrivedEvent event){
        addCancelOrder(event.getCancelOrder());
    }

    @Override
    public void enqueueBuyOrder(BuyOrder order) {
        buyOrders.offer(order);
    }

    @Override
    public void enqueueSellOrder(SellOrder order) {
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

    @SneakyThrows
    private void waitForOrders(){
        Thread.sleep(configuration.matchingEngineSleep());
    }

    private void checkForOrdersInQueue(){
        if(!thereIsTwoOrdersInBuyAndSell()) return;

        BuyOrder buyOrder = buyOrders.poll();
        SellOrder sellOrder = sellOrders.poll();

        var someOfThemAreCancelled = checkIfOrderIsCancelled(buyOrder) || checkIfOrderIsCancelled(sellOrder);

        if(someOfThemAreCancelled) return;

        if(matchingPriceEngine.isThereAnyMatch(buyOrder, sellOrder)){
            tradeProcessor.process(buyOrder, sellOrder);

            reenqueueIfSomeOrderWasntAllCompleted(buyOrder, sellOrder);
        }else{
            processMismatch(buyOrder, sellOrder);
        }
    }

    private boolean thereIsTwoOrdersInBuyAndSell(){
        return !(buyOrders.isEmpty() || sellOrders.isEmpty());
    }

    private boolean checkIfOrderIsCancelled(Order order){
        if(this.cancelOrders.containsKey(order.getOrderId())){
            System.out.println("1");

            CancelOrder cancelOrder = this.cancelOrders.remove(order.getOrderId());

            this.orderCancellator.cancel(order, cancelOrder);

            return true;
        }

        return false;
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
        boolean ttlExpiredBuyOrder = decreaseTtl(buyOrder);
        boolean ttlExpiredSellOrder = decreaseTtl(sellOrder);

        if(ttlExpiredBuyOrder) eventBus.publish(new ExceptionOccurredEvent(new TtlExpired(buyOrder))); else buyOrders.add(buyOrder);
        if(ttlExpiredSellOrder) eventBus.publish(new ExceptionOccurredEvent(new TtlExpired(sellOrder))); else sellOrders.add(sellOrder);
    }

    private boolean decreaseTtl(Order order){
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
}
