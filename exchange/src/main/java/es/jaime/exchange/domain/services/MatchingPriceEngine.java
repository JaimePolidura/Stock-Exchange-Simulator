package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;

public interface MatchingPriceEngine {
    boolean isThereAnyMatch(BuyOrder buyOrder, SellOrder sellOrder);

    double getPriceMatch(BuyOrder buyOrder, SellOrder sellOrder);
}
