package es.jaime.exchange.domain.models.messages;

public interface MessagePublisher {
    void publish(String exchange, String routingKey, Message message);
}
