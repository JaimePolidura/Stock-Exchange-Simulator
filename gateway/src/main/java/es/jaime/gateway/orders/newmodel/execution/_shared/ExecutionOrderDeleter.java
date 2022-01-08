package es.jaime.gateway.orders.newmodel.execution._shared;

import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.SellOrderRepostiry;
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
