package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExecutionOrderFinder {
    private final BuyOrderRepostory buyOrders;
    private final SellOrderRepostiry sellOrders;

    public Optional<ExecutionOrder> findById(OrderId orderId){
        Optional<BuyOrder> orderBuyOptional = buyOrders.findByOrderId(orderId);
        Optional<SellOrder> orderSellOptional = sellOrders.findByOrderId(orderId);

        return orderBuyOptional
                .map(orderBuy -> (ExecutionOrder) orderBuy)
                .or(() -> orderSellOptional.map(orderSell -> (ExecutionOrder) orderSell));
    }

    public List<ExecutionOrder> findByClientIdAndState(OrderClientId clientId, OrderState state){
        List<BuyOrder> orderBuyList = buyOrders.findByOrderClientIdAndState(clientId, state);
        List<SellOrder> orderSellList = sellOrders.findByOrderClientIdAndState(clientId, state);

        List<ExecutionOrder> toReturn = new ArrayList<>(orderBuyList);
        toReturn.addAll(orderSellList);

        return toReturn;
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
