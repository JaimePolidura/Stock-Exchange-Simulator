package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messagePublisher.CommandMessage;
import es.jaime.gateway._shared.domain.messagePublisher.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("rabbitmq-starter")
public class RabbitMQStarter implements CommandLineRunner {
    private final QueuePublisherRabbitMQ publisherRabbitMQ;

    public RabbitMQStarter(QueuePublisherRabbitMQ publisherRabbitMQ) {
        this.publisherRabbitMQ = publisherRabbitMQ;
    }

    @Override
    public void run(String... args) {
        publisherRabbitMQ.publish("sxs.start", "sxs.start", new StartQueueMessage());
    }

    private static class StartQueueMessage implements CommandMessage {
        @Override
        public String name() {
            return "start";
        }

        @Override
        public Map<String, Object> body() {
            return new HashMap<>();
        }

        @Override
        public String routingKey() {
            return RabbitMQDeclarables.start;
        }
    }
}
