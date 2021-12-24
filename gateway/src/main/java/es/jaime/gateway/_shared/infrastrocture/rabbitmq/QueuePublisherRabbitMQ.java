package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.queue.QueueMessage;
import es.jaime.gateway._shared.domain.queue.QueuePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQConfiguration.*;

@Service
public class QueuePublisherRabbitMQ implements QueuePublisher {
    private final RabbitTemplate rabbitTemplate;

    public QueuePublisherRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enqueue(QueueMessage queueMessage) {
        this.rabbitTemplate.convertAndSend(newOrders, queueMessage.routingKey(), queueMessage.toJson());
    }
}
