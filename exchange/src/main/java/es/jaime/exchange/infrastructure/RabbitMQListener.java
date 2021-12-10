package es.jaime.exchange.infrastructure;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.nio.charset.StandardCharsets;

public final class RabbitMQListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody(), StandardCharsets.UTF_8));
    }
}
