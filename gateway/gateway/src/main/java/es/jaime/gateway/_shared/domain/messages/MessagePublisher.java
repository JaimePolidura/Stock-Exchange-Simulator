package es.jaime.gateway._shared.domain.messages;

public interface MessagePublisher {
    void publish(String exchange, String routingKey, Message message);
}
