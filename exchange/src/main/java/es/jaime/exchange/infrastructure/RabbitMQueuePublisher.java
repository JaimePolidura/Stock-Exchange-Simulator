package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.services.MessagePublisher;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQueuePublisher implements MessagePublisher {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQueuePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publish(String queue, Message message) {
        this.rabbitTemplate.convertAndSend(queue, queue + ".*", toJSON(message));
    }

    private String toJSON(Message message){
        return new JSONObject(message.toPrimitives()).toString();
    }
}
