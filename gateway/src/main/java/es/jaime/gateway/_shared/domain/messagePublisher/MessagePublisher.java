package es.jaime.gateway._shared.domain.messagePublisher;

public interface MessagePublisher {
    void publish(String exchange, String routingKey, Message message);
}
