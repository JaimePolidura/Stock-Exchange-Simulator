package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;

public interface TradeProcessor {
    void process(BuyOrder buyOrder, SellOrder sellOrder);
}
