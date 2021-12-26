package es.jaime.gateway.activeorders.onexecutedorder;

import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQConfiguration.*;

public final class OnExecutedOrder {
    private final ActiveOrderRepository repository;

    public OnExecutedOrder(ActiveOrderRepository activeOrderRepository) {
        this.repository = activeOrderRepository;
    }

    @RabbitListener(queues = executedOrders)
    public void on (String body){
        JSONObject jsonObject = new JSONObject(body);

    }
}
