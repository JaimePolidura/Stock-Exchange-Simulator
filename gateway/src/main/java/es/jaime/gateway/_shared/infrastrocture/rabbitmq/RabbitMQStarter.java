package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.messagePublisher.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("rabbitmq-starter")
public class RabbitMQStarter implements CommandLineRunner {
    private final QueuePublisherRabbitMQ publisherRabbitMQ;

    public RabbitMQStarter(QueuePublisherRabbitMQ publisherRabbitMQ) {
        this.publisherRabbitMQ = publisherRabbitMQ;
    }

    @Override
    public void run(String... args) {
        publisherRabbitMQ.publish(new StartQueueMessage());
    }

    private static class StartQueueMessage implements Message {
        @Override
        public String toJson() {
            return "start";
        }

        @Override
        public String routingKey() {
            return RabbitMQConfiguration.start;
        }
    }
}
