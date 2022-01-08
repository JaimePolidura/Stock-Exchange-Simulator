package es.jaime.gateway.orders.newmodel.cancel.onerrororder;

import es.jaime.gateway.orders.newmodel._shared.domain.OrderDate;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderState;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderType;
import es.jaime.gateway.orders.newmodel.cancel._shared.domain.OrderCancel;
import es.jaime.gateway.orders.newmodel.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders.orders._shared.domain.events.ErrorDuringOrderExecution;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OnErrorOrer {
    private final OrdersCancelRepository cancelOrders;

    @EventListener({ErrorDuringOrderExecution.class})
    public void on(ErrorDuringOrderExecution event){
        Optional<OrderCancel> orderOptional = cancelOrders.findById(OrderId.of(event.getOrderId()));

        if(orderOptional.isPresent()){
            OrderCancel orderCancel = orderOptional.get();

            cancelOrders.save(new OrderCancel(
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
