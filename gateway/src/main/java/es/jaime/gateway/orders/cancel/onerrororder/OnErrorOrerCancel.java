package es.jaime.gateway.orders.cancel.onerrororder;

import es.jaime.gateway.orders.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderType;
import es.jaime.gateway.orders._shared.domain.events.ErrorDuringOrderExecution;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OnErrorOrerCancel {
    private final OrdersCancelRepository cancelOrders;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<CancelOrder> orderOptional = cancelOrders.findById(OrderId.of(event.getOrderId()));

        if(orderOptional.isPresent()){
            CancelOrder orderCancel = orderOptional.get();

            cancelOrders.save(new CancelOrder(
                    orderCancel.getOrderId(),
                    orderCancel.getClientId(),
                    OrderDate.now(),
                    OrderType.cancel(),
                    OrderState.error(),
                    orderCancel.getTicker(),
                    orderCancel.getOrderIdToCancel()
            ));
        }

    }
}
