package es.jaime.gateway.orders.onorderexecuted;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders._shared.domain.events.OrderExecuted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnOrderExecuted {
    private final OrdersRepository repository;

    public OnOrderExecuted(OrdersRepository repository) {
        this.repository = repository;
    }

    @EventListener({OrderExecuted.class})
    public void on(OrderExecuted event){
        String orderId = event.getOrderId();
        int quantity = event.getQuantity();

        Optional<Order> orderOptional = repository.findByOrderId(OrderID.of(orderId));

        if(orderOptional.isEmpty()){
            //TODO
            return;
        }

        Order order = orderOptional.get();

        if(order.quantity().value() > quantity)
            updateQuantityOrder(order, quantity);
        else
            removeOrder(order.orderId());
    }

    private void updateQuantityOrder(Order order, int quantity){
        repository.save(new Order(
                order.orderId(),
                order.ticker(),
                order.clientId(),
                order.date(),
                OrderQuantity.of(order.quantity().value() - quantity),
                order.type(),
                order.executionPrice()
        ));
    }

    private void removeOrder(OrderID orderId){
        repository.deleteByOrderId(orderId);
    }
}
