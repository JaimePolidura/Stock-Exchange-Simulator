package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.Order;

public interface TradeProcessor {
    void process(Order buyOrder, Order sellOrder);
}
