package es.jaime.gateway.orders.newmodel.execution.sell.onerrororder;

import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderState;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.OrderBuy;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.orders._shared.domain.events.ErrorDuringOrderExecution;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OnErrorOrer {
    private final SellOrderRepostiry sellOrdes;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<OrderSell> orderOptional = sellOrdes.findByOrderId(OrderId.of(event.getOrderId()));

        if(orderOptional.isPresent()){
            OrderSell sellOrder = orderOptional.get();

            sellOrdes.save(new OrderSell(
                    sellOrder.getOrderId(),
                    sellOrder.getClientId(),
                    sellOrder.getDate(),
                    sellOrder.getType(),
                    OrderState.error(),
                    sellOrder.getTicker(),
                    sellOrder.getQuantity(),
                    sellOrder.getExecutionPrice(),
                    sellOrder.getPositionIdToSell()
            ));
        }

    }
}
