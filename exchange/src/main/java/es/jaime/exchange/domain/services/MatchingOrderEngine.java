package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;

import java.util.Queue;

public interface MatchingOrderEngine extends Runnable {
    void enqueueBuyOrder(BuyOrder order);

    void enqueueSellOrder(SellOrder order);

    Queue<BuyOrder> getBuyOrdersQueue();
    Queue<SellOrder> getSellOrdersQueue();

    void stop();
}
