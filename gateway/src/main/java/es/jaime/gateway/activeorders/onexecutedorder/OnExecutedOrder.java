package es.jaime.gateway.activeorders.onexecutedorder;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderID;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderQuantity;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"rabbitmq-starter"})
public class OnExecutedOrder {
    private final ActiveOrderRepository repository;

    public OnExecutedOrder(ActiveOrderRepository activeOrderRepository) {
        this.repository = activeOrderRepository;
    }

    @RabbitListener(queues = "sxs.executed-orders.gateway")
    public void on (String body){
        System.out.println("Hola");

        JSONObject jsonObject = new JSONObject(body);
        String orderId = jsonObject.getJSONObject("body").getString("orderId");
        int quantity = jsonObject.getJSONObject("body").getInt("quantity");

        ActiveOrder order = repository.findByOrderId(ActiveOrderID.of(orderId))
                .orElseThrow(() -> new ResourceNotFound("Order not found"));

        if(order.quantity().value() > quantity)
            updateQuantityOrder(order, quantity);
        else
            removeOrder(order.activeorderId());
    }

    private void updateQuantityOrder(ActiveOrder order, int quantity){
        repository.save(new ActiveOrder(
                order.activeorderId(),
                order.ticker(),
                order.clientId(),
                order.date(),
                ActiveOrderQuantity.of(order.quantity().value() - quantity),
                order.type(),
                order.executionPrice(),
                order.status()
        ));
    }

    private void removeOrder(ActiveOrderID orderId){
        repository.deleteByOrderId(orderId);
    }
}
