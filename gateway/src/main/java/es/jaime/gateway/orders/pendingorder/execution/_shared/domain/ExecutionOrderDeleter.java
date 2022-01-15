package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExecutionOrderDeleter {
    private final BuyOrderRepostory buyOrders;
    private final BuyOrderRepostory sellOrders;

    public void deleteById(OrderId orderId){
        buyOrders.deleteByOrderId(orderId);
        sellOrders.deleteByOrderId(orderId);
    }
}
