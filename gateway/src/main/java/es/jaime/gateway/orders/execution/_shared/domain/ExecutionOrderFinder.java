package es.jaime.gateway.orders.execution._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExecutionOrderFinder {
    private final BuyOrderRepostory buyOrders;
    private final SellOrderRepostiry sellOrders;

    public ExecutionOrderFinder(BuyOrderRepostory buyOrders, SellOrderRepostiry sellOrders) {
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public Optional<ExecutionOrder> findById(OrderId orderId){
        Optional<BuyOrder> orderBuyOptional = buyOrders.findByOrderId(orderId);
        Optional<SellOrder> orderSellOptional = sellOrders.findByOrderId(orderId);

        return orderBuyOptional
                .map(orderBuy -> (ExecutionOrder) orderBuy)
                .or(() -> orderSellOptional.map(orderSell -> (ExecutionOrder) orderSell));
    }

    public Optional<ExecutionOrder> findLastOrderFor(OrderTicker ticker){
        Optional<BuyOrder> orderBuyOptional = buyOrders.findLastOrderByStateAndByTicker(OrderState.executed(), ticker);
        Optional<SellOrder> orderSellOptional = sellOrders.findLastOrderByStateAndByTicker(OrderState.executed(), ticker);

        if(orderBuyOptional.isEmpty() && orderSellOptional.isEmpty()){
            return Optional.empty();
        }
        if(orderBuyOptional.isEmpty()){
            return orderSellOptional.map(sellOrder -> (ExecutionOrder) sellOrder);
        }
        if(orderSellOptional.isEmpty()){
            return orderBuyOptional.map(buyOrder -> (ExecutionOrder) buyOrder);
        }

        LocalDateTime dateOfBuyOrder = LocalDateTime.parse(orderBuyOptional.get().getDate().value());
        LocalDateTime dateOfSellOrder = LocalDateTime.parse(orderSellOptional.get().getDate().value());

        return dateOfBuyOrder.compareTo(dateOfSellOrder) >= 0 ?
                Optional.of(orderBuyOptional.get()) :
                Optional.of(orderSellOptional.get());
    }
}