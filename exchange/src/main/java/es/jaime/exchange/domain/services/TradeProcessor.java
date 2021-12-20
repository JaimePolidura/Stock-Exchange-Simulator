package es.jaime.exchange.domain;

import es.jaime.exchange.domain.models.Order;

public interface TradeProcessor {
    void process(Order buyOrder, Order sellOrder);
}
