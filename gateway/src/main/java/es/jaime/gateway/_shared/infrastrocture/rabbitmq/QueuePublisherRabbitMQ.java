package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messagePublisher.Message;
import es.jaime.gateway._shared.domain.messagePublisher.MessagePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQConfiguration.*;

@Service
public class QueuePublisherRabbitMQ implements MessagePublisher {
    private final RabbitTemplate rabbitTemplate;

    public QueuePublisherRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(Message message) {
        this.rabbitTemplate.convertAndSend(newOrders, message.routingKey(), message.toJson());
    }
}
