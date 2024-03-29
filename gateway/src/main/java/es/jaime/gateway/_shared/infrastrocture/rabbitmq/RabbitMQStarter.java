package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messages.CommandMessage;
import es.jaime.gateway._shared.domain.messages.MessageNames;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Component("rabbitmq-starter")
@AllArgsConstructor
@Order(1)
public class RabbitMQStarter implements CommandLineRunner {
    private final QueuePublisherRabbitMQ publisherRabbitMQ;

    @Override
    public void run(String... args) {
        publisherRabbitMQ.publish(START_EXCHANGE, START_EXCHANGE, new StartQueueMessage());
    }

    private static class StartQueueMessage implements CommandMessage {
        @Override
        public MessageNames name() {
            return MessageNames.START_SERVICES;
        }

        @Override
        public Map<String, Object> body() {
            return new HashMap<>();
        }
    }
}
