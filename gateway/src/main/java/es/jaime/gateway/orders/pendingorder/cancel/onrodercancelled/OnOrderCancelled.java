package es.jaime.gateway.orders.pendingorder.cancel.onrodercancelled;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderCancelled;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OnOrderCancelled {
    private final OrdersCancelRepository ordersCancel;

    @EventListener({OrderCancelled.class})
    public void on(OrderCancelled orderCancelled){
        CancelOrder orderCancel = ordersCancel.findByOrderId(OrderId.of(orderCancelled.getOrderId()))
                .orElseThrow(() -> new ResourceNotFound("Order not found error"));

        ordersCancel.save(new CancelOrder(
                orderCancel.getOrderId(),
                orderCancel.getClientId(),
                OrderDate.now(),
                OrderState.executed(),
                orderCancel.getTicker(),
                PendingOrderType.cancel(),
                orderCancel.getOrderIdToCancel()
        ));
    }
}
