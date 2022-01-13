package es.jaime.gateway.orders.pendingorder.execution._shared.getlastprice;

import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderExecuted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LastPriceService {
    private final Map<String, Double> cacheLastPrices;
    private final ExecutionOrderFinder executionOrderFinder;

    public LastPriceService(ExecutionOrderFinder executionOrderFinder) {
        this.executionOrderFinder = executionOrderFinder;
        this.cacheLastPrices = new ConcurrentHashMap<>();
    }

    public OrderPriceToExecute getLastPriceFor(OrderTicker ticker){
        return isCached(ticker) ?
                getLastPriceFromCache(ticker) :
                getLastPriceFromRepository(ticker) ;
    }

    private OrderPriceToExecute getLastPriceFromRepository(OrderTicker ticker){
        Optional<ExecutionOrder> lastOrderExecutedOptional = executionOrderFinder.findLastOrderFor(ticker);

        if(lastOrderExecutedOptional.isEmpty()){
            return OrderPriceToExecute.of(1);
        }
        ExecutionOrder lastOrderExecuted = lastOrderExecutedOptional.get();

        this.cacheLastPrices.put(lastOrderExecuted.getTicker().value(), lastOrderExecuted.getPriceToExecute().value());

        return lastOrderExecuted.getPriceToExecute();
    }

    private boolean isCached(OrderTicker orderTicker){
        return this.cacheLastPrices.containsKey(orderTicker.value());
    }

    private OrderPriceToExecute getLastPriceFromCache(OrderTicker ticker){
        return OrderPriceToExecute.of(this.cacheLastPrices.get(ticker.value()));
    }

    @EventListener({OrderExecuted.class})
    protected void on(OrderExecuted event){
        cacheLastPrices.put(event.getTicker(), event.getExecutionPrice());
    }
}
