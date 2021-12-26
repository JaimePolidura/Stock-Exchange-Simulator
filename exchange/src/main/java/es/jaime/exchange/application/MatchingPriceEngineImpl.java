package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.services.MatchingPriceEngine;
import org.springframework.stereotype.Service;

@Service
public final class MatchingPriceEngineImpl implements MatchingPriceEngine {
    @Override
    public boolean isThereAnyMatch(Order buyOrder, Order sellOrder) {
        boolean bothTypeMarket =  buyOrder.getExecutionPrice() == -1 && sellOrder.getExecutionPrice() == -1;
        boolean bothTypeLimit = buyOrder.getExecutionPrice() != -1 && sellOrder.getExecutionPrice() != -1;
        boolean buyOrderExecutionPriceSmallerThanSellOrder = buyOrder.getExecutionPrice() < sellOrder.getExecutionPrice();

        return !(bothTypeMarket || (bothTypeLimit && buyOrderExecutionPriceSmallerThanSellOrder));
    }

    @Override
    public double getPriceMatch(Order buyOrder, Order sellOrder) {
        if(buyOrder.getExecutionPrice() != -1 && sellOrder.getExecutionPrice() != -1)
            return buyOrder.getExecutionPrice();
        else if(buyOrder.getExecutionPrice() == -1)
            return sellOrder.getExecutionPrice();
        else
            return buyOrder.getExecutionPrice();
    }
}