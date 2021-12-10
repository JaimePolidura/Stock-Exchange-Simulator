package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.QueueMessage;
import es.jaime.exchange.domain.QueuePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQueuePublisher implements QueuePublisher {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQueuePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void enqueue(QueueMessage queueMessage) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName,
                RabbitMQConfiguration.topicExchangeName,
                queueMessage.toJson());
    }
}
