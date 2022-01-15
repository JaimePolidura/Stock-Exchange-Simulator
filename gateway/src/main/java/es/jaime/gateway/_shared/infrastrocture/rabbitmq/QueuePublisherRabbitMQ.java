package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messages.Message;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QueuePublisherRabbitMQ implements MessagePublisher {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(String exchange, String routingKey, Message message) {
        System.out.println(toJSON(message));

        this.rabbitTemplate.convertAndSend(exchange, routingKey, toJSON(message));
    }

    private String toJSON(Message message){
        return new JSONObject(message.toPrimitives()).toString();
    }
}
