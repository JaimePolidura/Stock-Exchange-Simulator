package es.jaime.gateway.orders.onerrororder;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQConfiguration.*;

public final class OnErrorOrder {
    @RabbitListener(queues = errorOrders)
    public void on(String body){
        //TODO
    }
}
