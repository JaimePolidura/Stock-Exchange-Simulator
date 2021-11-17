package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.bus.queue.QueueMessage;
import es.jaime.gateway._shared.domain.bus.queue.QueuePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public final class QueuePublisherRabbitMQ implements QueuePublisher {
    private final RabbitTemplate rabbitTemplate;

    public QueuePublisherRabbitMQ(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enqueue(QueueMessage queueMessage) {
        this.rabbitTemplate.convertAndSend(queueMessage.toPrimitives());
    }
}
