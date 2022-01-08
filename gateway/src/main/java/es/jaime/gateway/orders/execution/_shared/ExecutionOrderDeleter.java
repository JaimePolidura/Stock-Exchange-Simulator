package es.jaime.gateway.orders.execution._shared;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExecutionOrderDeleter {
    private final BuyOrderRepostiry buyOrders;
    private final SellOrderRepostiry sellOrders;

    public void delteByOrderId(OrderId orderId){
    }
}
