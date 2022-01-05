package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;

import java.util.Map;
import java.util.Queue;

public interface MatchingOrderEngine extends Runnable {
    void addBuyOrder(BuyOrder order);
    void addSellOrder(SellOrder order);
    void addCancelOrder(CancelOrder order);

    Queue<BuyOrder> getBuyOrdersQueue();
    Queue<SellOrder> getSellOrdersQueue();
    Map<String, CancelOrder> getCancelOrders();

    void stop();
}
