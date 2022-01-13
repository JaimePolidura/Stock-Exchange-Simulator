package es.jaime.gateway.orders.pendingorder.cancel.onrodercancelled;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderIdToCancel;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderCancelled;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
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
        CancelOrder orderCancel = ordersCancel.findById(OrderId.of(orderCancelled.getOrderId()))
                .orElseThrow(() -> new ResourceNotFound("Order not found error"));

        ordersCancel.save(new CancelOrder(
                orderCancel.getOrderId(),
                orderCancel.getClientId(),
                OrderDate.now(),
                PendingOrderType.cancel(),
                OrderState.executed(),
                orderCancel.getTicker(),
                OrderIdToCancel.of(orderCancelled.getOrderIdCancelled())
        ));
    }
}
