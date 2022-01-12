package es.jaime.gateway.orders.execution.sell.onerrororder;

import es.jaime.gateway.orders._shared.domain.events.ErrorDuringOrderExecution;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OnErrorOrderSell {
    private final SellOrderRepostiry sellOrdes;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<SellOrder> orderOptional = sellOrdes.findByOrderId(OrderId.of(event.getOrderId()));

        if(orderOptional.isPresent()){
            SellOrder sellOrder = orderOptional.get();

            sellOrdes.save(new SellOrder(
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
