package es.jaime.gateway.orders.orders.onordercancelled;

import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.orders._shared.domain.events.OrderCancelled;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnOrderCancelled {
    private final OrdersRepository orders;

    public OnOrderCancelled(OrdersRepository orders) {
        this.orders = orders;
    }

    @EventListener({OrderCancelled.class})
    public void on(OrderCancelled orderCancelled){
        OrderId orderIdCancelled = OrderId.of(orderCancelled.getOrderIdCancelled());
        OrderId orderId = OrderId.of(orderCancelled.getOrderId());

        orders.deleteByOrderId(orderIdCancelled);
        orders.deleteByOrderId(orderId);
    }
}
