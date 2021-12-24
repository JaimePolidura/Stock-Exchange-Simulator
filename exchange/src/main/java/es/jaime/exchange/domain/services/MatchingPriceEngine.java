package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.Order;

public interface MatchingPriceEngine {
    boolean isThereAnyMatch(Order buyOrder, Order sellOrder);

    double getPriceMatch(Order buyOrder, Order sellOrder);
}
