package es.jaime.gateway.orders.execution.buy.onerrororder;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders._shared.domain.events.ErrorDuringOrderExecution;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OnErrorOrerBuy {
    private final BuyOrderRepostiry buyOrders;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<BuyOrder> orderOptional = buyOrders.findByOrderId(OrderId.of(event.getOrderId()));

        if(orderOptional.isPresent()){
            BuyOrder buyOrder = orderOptional.get();

            buyOrders.save(new BuyOrder(
                    buyOrder.getOrderId(),
                    buyOrder.getClientId(),
                    buyOrder.getDate(),
                    buyOrder.getType(),
                    OrderState.error(),
                    buyOrder.getTicker(),
                    buyOrder.getQuantity(),
                    buyOrder.getExecutionPrice()
            ));
        }

    }
}
