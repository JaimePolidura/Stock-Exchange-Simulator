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
import java.util.Collections;
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

    public List<ExecutionOrder> findByTicker(OrderTicker ticker){
        List<BuyOrder> orderBuyList = buyOrders.findByTicker(ticker);
        List<SellOrder> orderSellList = sellOrders.findByTicker(ticker);

        List<ExecutionOrder> toReturn = new ArrayList<>();

        toReturn.addAll(orderSellList);
        toReturn.addAll(orderBuyList);

        return toReturn;
    }

    public List<ExecutionOrder> findByClientIdAndState(OrderClientId clientId, OrderState state){
        List<BuyOrder> orderBuyList = buyOrders.findByOrderClientIdAndState(clientId, state);
        List<SellOrder> orderSellList = sellOrders.findByOrderClientIdAndState(clientId, state);

        List<ExecutionOrder> toReturn = new ArrayList<>(orderBuyList);
        toReturn.addAll(orderSellList);

        return toReturn;
    }

    public List<ExecutionOrder> findByOrdersId(List<OrderId> orderIds){
        if(orderIds.size() == 0) return Collections.EMPTY_LIST;

        List<BuyOrder> orderBuyList = buyOrders.findByOrdersId(orderIds);
        List<SellOrder> orderSellList = sellOrders.findByOrdersId(orderIds);

        List<ExecutionOrder> toReturn = new ArrayList<>(orderBuyList);
        toReturn.addAll(orderSellList);

        return toReturn;
    }
}
