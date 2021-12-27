package es.jaime.gateway.orders.onexecutedorder;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"rabbitmq-starter"})
public class OnExecutedOrder {
    private final OrdersRepository repository;

    public OnExecutedOrder(OrdersRepository ordersRepository) {
        this.repository = ordersRepository;
    }

    @RabbitListener(queues = "sxs.executed-orders.gateway")
    public void on (String body){
        JSONObject jsonObject = new JSONObject(body);
        String orderId = jsonObject.getJSONObject("body").getString("orderId");
        int quantity = jsonObject.getJSONObject("body").getInt("quantity");

        Order order = repository.findByOrderId(OrderID.of(orderId))
                .orElseThrow(() -> new ResourceNotFound("Order not found"));

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
                order.executionPrice(),
                order.status()
        ));
    }

    private void removeOrder(OrderID orderId){
        repository.deleteByOrderId(orderId);
    }
}
