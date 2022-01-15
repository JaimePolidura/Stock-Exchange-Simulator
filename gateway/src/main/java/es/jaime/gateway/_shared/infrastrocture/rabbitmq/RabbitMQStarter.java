package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messages.CommandMessage;
import es.jaime.gateway._shared.domain.messages.MessageNames;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("rabbitmq-starter")
@DependsOn({"rabbitmq-declarables"})
@AllArgsConstructor
public class RabbitMQStarter implements CommandLineRunner {
    private final QueuePublisherRabbitMQ publisherRabbitMQ;

    @Override
    public void run(String... args) {
        publisherRabbitMQ.publish("sxs.start", "sxs.start", new StartQueueMessage());
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
