package es.jaime.gateway.orders.orders.onorderexecuted;

import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderBody;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.orders._shared.domain.events.OrderExecuted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
public class OnOrderExecuted {
    private final OrdersRepository repository;

    public OnOrderExecuted(OrdersRepository repository) {
        this.repository = repository;
    }

    @EventListener({OrderExecuted.class})
    @org.springframework.core.annotation.Order(100)
    public void on(OrderExecuted event){
        Optional<Order> orderOptional = repository.findByOrderId(OrderId.of(event.getOrderId()));

        if(orderOptional.isEmpty()) return;

        Order order = orderOptional.get();
        OrderBody orderBody = order.getBody();

        if(orderBody.getInteger("quantity") > event.getQuantity())
            updateQuantityOrder(order, event.getQuantity());
        else
            removeOrder(order.getOrderId());
    }

    private void updateQuantityOrder(Order order, int quantity){
        int newQuantity = order.getBody().getInteger("quantity") - quantity;
        Map<String, Object> newObjectBody = order.getBody().toMap();
        newObjectBody.replace("quantity", newQuantity);

        repository.save(new Order(
                order.getOrderId(),
                order.getClientId(),
                order.getDate(),
                order.getOrderTypeId(),
                OrderBody.of(newObjectBody)
        ));
    }

    private void removeOrder(OrderId orderId){
        repository.deleteByOrderId(orderId);
    }
}
