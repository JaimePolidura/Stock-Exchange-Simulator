package es.jaime.gateway._shared.domain.bus.queue;

public interface QueuePublisher {
    void enqueue(String queueName, QueueMessage queueMessage);
}
