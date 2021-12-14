package es.jaime.exchange.application;

import es.jaime.exchange.domain.*;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;
import java.util.Queue;

@Service
public class MatchingEngineImpl implements MatchingEngine, Runnable {
    private final Queue<Order> buyOrders;
    private final Queue<Order> sellOrders;
    private final TradeProcessor tradeProcessor;

    public MatchingEngineImpl(TradeProcessor tradeProcessor){
        this.tradeProcessor = tradeProcessor;
        this.buyOrders = new PriorityQueue<>();
        this.sellOrders = new PriorityQueue<>();
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
        while(true) {
           Order buyOrder = buyOrders.poll();
           Order sellOrder = sellOrders.poll();

            if(isMatched(buyOrder, sellOrder)){
                performTrade(buyOrder, sellOrder);
            }else{
                processMismatch(buyOrder, sellOrder);
            }
        }
    }

    private boolean isMatched(Order buyOrder, Order sellOrder) {
        return (buyOrder.getExecutionPrice() >= sellOrder.getExecutionPrice()) &&
                (!(buyOrder.getExecutionPrice() == -1) || !(sellOrder.getExecutionPrice() == -1));
    }

    private void processMismatch(Order buyOrder, Order sellOrder) {
        int actualTtlBuyOrder = buyOrder.decreaseTtlByOne();
        int actualTtlSellOrder = sellOrder.decreaseTtlByOne();

        if(actualTtlBuyOrder <= 0){

        }
        if(actualTtlSellOrder <= 0){

        }
    }

    private void performTrade(Order buyOrder, Order sellOrder) {
        try {
            tradeProcessor.process(buyOrder, sellOrder);
        }catch (Exception e){
            buyOrder.decreaseTtlByOne();
            sellOrder.decreaseTtlByOne();

            enqueue(buyOrder);
            enqueue(sellOrder);

            e.printStackTrace();
        }
    }
}
