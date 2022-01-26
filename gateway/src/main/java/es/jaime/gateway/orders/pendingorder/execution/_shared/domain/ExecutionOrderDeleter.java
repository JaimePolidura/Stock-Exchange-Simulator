package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExecutionOrderDeleter {
    private final BuyOrderRepostory buyOrders;
    private final SellOrderRepostiry sellOrders;

    public void deleteById(ExecutionOrder order) {
        if(order.getPendingOrderType().isBuy())
            buyOrders.deleteByOrderId(order.getOrderId());
        else
            sellOrders.deleteByOrderId(order.getOrderId());
    }
}
