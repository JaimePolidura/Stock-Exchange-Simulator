package es.jaime.exchange._shared;

import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.services.MessagePublisher;

import java.util.LinkedList;
import java.util.Queue;

public class QueuePublisherMock implements MessagePublisher {
    private final Queue<Message> queue;

    public QueuePublisherMock(){
        this.queue = new LinkedList<>();
    }

    public Queue<Message> getQueue() {
        return queue;
    }

    @Override
    public void publish(String exchange, String queue, Message message) {
        this.queue.offer(message);
    }
}
