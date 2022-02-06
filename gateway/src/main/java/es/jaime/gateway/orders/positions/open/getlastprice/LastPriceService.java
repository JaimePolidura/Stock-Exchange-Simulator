package es.jaime.gateway.orders.positions.open.getlastprice;

import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderExecuted;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionFinder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LastPriceService {
    private final Map<String, Double> cacheLastPrices;
    private final OpenPositionFinder openPositionFinder;

    public LastPriceService(OpenPositionFinder openPositions) {
        this.openPositionFinder = openPositions;
        this.cacheLastPrices = new ConcurrentHashMap<>();
    }

    public PositionOpeningPrice getLastPriceFor(OrderTicker ticker){
        return isCached(ticker) ?
                getLastPriceFromCache(ticker) :
                getLastPriceFromRepository(ticker) ;
    }

    private PositionOpeningPrice getLastPriceFromRepository(OrderTicker ticker){
        Optional<OpenPosition> lastOpenPositionOpenedOptional = openPositionFinder.findLastOpenPositionsByTicker(ticker);

        if(lastOpenPositionOpenedOptional.isEmpty()) return PositionOpeningPrice.of(1);

        OpenPosition lastOpenPositionOpened = lastOpenPositionOpenedOptional.get();

        this.cacheLastPrices.put(lastOpenPositionOpened.getTicker().value(), lastOpenPositionOpened.getOpeningPrice().value());

        return lastOpenPositionOpened.getOpeningPrice();
    }

    private boolean isCached(OrderTicker orderTicker){
        return this.cacheLastPrices.containsKey(orderTicker.value());
    }

    private PositionOpeningPrice getLastPriceFromCache(OrderTicker ticker){
        return PositionOpeningPrice.of(this.cacheLastPrices.get(ticker.value()));
    }

    @EventListener({ExecutionOrderExecuted.class})
    protected void on(ExecutionOrderExecuted event){
        cacheLastPrices.put(event.getTicker(), event.getPriceToExecute());
    }
}
