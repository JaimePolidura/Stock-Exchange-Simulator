package es.jaime.gateway.orders.execution._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExecutionOrderFinder {
    private final BuyOrderRepostiry buyOrders;
    private final SellOrderRepostiry sellOrders;

    public ExecutionOrderFinder(BuyOrderRepostiry buyOrders, SellOrderRepostiry sellOrders) {
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
}
