package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messagePublisher.Message;
import es.jaime.gateway._shared.domain.messagePublisher.MessagePublisher;
import org.json.JSONObject;
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
    public void publish(String exchange, String routingKey, Message message) {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, toJSON(message));
    }

    private String toJSON(Message message){
        return new JSONObject(message.all()).toString();
    }
}
