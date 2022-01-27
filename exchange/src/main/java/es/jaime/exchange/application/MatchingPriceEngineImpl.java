package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.SellOrder;
import es.jaime.exchange.domain.services.MatchingPriceEngine;
import org.springframework.stereotype.Service;

@Service
public final class MatchingPriceEngineImpl implements MatchingPriceEngine {
    @Override
    public boolean isThereAnyMatch(BuyOrder buyOrder, SellOrder sellOrder) {
        boolean bothTypeMarket =  buyOrder.getPriceToExecute() == -1 && sellOrder.getPriceToExecute() == -1;
        boolean bothTypeLimit = buyOrder.getPriceToExecute() != -1 && sellOrder.getPriceToExecute() != -1;
        boolean buyOrderExecutionPriceSmallerThanSellOrder = buyOrder.getPriceToExecute() < sellOrder.getPriceToExecute();

        return !(bothTypeMarket || (bothTypeLimit && buyOrderExecutionPriceSmallerThanSellOrder));
    }

    @Override
    public double getPriceMatch(BuyOrder buyOrder, SellOrder sellOrder) {
        if(buyOrder.getPriceToExecute() != -1 && sellOrder.getPriceToExecute() != -1)
            return buyOrder.getPriceToExecute();
        else if(buyOrder.getPriceToExecute() == -1)
            return sellOrder.getPriceToExecute();
        else
            return buyOrder.getPriceToExecute();
    }
}
