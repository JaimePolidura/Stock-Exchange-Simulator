package es.jaime.gateway.orders.newmodel.cancel.onrodercancelled;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders.newmodel._shared.OrderDate;
import es.jaime.gateway.orders.newmodel._shared.OrderIdToCancel;
import es.jaime.gateway.orders.newmodel._shared.OrderState;
import es.jaime.gateway.orders.newmodel._shared.OrderType;
import es.jaime.gateway.orders.newmodel.cancel._shared.domain.OrderCancel;
import es.jaime.gateway.orders.newmodel.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders.orders._shared.domain.events.OrderCancelled;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnOrderCancelled {
    private final OrdersCancelRepository ordersCancel;

    public OnOrderCancelled(OrdersCancelRepository ordersCancel) {
        this.ordersCancel = ordersCancel;
    }

    @EventListener({OrderCancelled.class})
    public void on(OrderCancelled orderCancelled){
        OrderCancel orderCancel = ordersCancel.findById(es.jaime.gateway.orders.newmodel._shared.OrderId.of(orderCancelled.getOrderId()))
                .orElseThrow(() -> new ResourceNotFound("Order not found error"));

        ordersCancel.save(new OrderCancel(
                orderCancel.getOrderId(),
                orderCancel.getClientId(),
                OrderDate.now(),
                OrderType.cancel(),
                OrderState.executed(),
                orderCancel.getTicker(),
                OrderIdToCancel.of(orderCancelled.getOrderIdCancelled())
        ));
    }
}
