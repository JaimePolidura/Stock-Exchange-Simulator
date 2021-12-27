package es.jaime.gateway._shared.domain.messagePublisher;

public interface MessagePublisher {
    void publish(Message queueMessage);
}
