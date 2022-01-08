package es.jaime.gateway.orders.newmodel.execution.buy.onerrororder;

import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderState;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.OrderBuy;
import es.jaime.gateway.orders.orders._shared.domain.events.ErrorDuringOrderExecution;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OnErrorOrer {
    private final BuyOrderRepostiry buyOrders;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<OrderBuy> orderOptional = buyOrders.findByOrderId(OrderId.of(event.getOrderId()));

        if(orderOptional.isPresent()){
            OrderBuy buyOrder = orderOptional.get();

            buyOrders.save(new OrderBuy(
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
