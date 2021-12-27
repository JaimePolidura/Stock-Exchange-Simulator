package es.jaime.gateway.orders.onerrororder;

import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;


@Component
@DependsOn({"rabbitmq-starter"})
public class OnErrorOrder {
    private final OrdersRepository ordersRepository;

    public OnErrorOrder(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @RabbitListener(queues = "sxs.error-orders.gateway")
    public void on(String body){
        JSONObject jsonObject = new JSONObject(body);
        String orderId = jsonObject.getJSONObject("body").getString("orderId");

        ordersRepository.deleteByOrderId(OrderID.of(orderId));
    }
}
