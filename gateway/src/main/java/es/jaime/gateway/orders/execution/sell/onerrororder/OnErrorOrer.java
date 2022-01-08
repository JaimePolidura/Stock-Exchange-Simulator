package es.jaime.gateway.orders.execution.sell.onerrororder;

import es.jaime.gateway.orders._shared.domain.ErrorDuringOrderExecution;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
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
