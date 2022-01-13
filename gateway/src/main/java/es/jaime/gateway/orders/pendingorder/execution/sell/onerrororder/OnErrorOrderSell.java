package es.jaime.gateway.orders.pendingorder.execution.sell.onerrororder;

import es.jaime.gateway.orders.pendingorder._shared.domain.events.ErrorDuringOrderExecution;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
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
                    OrderState.error(),
                    sellOrder.getTicker(),
                    sellOrder.getPendingOrderType(),
                    sellOrder.getQuantity(),
                    sellOrder.getPriceToExecute(),
                    sellOrder.getPositionIdToSell()
            ));
        }
    }
}
