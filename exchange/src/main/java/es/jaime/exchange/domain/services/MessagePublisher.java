package es.jaime.exchange.domain.services;

import es.jaime.exchange.domain.models.messages.Message;

public interface MessagePublisher {
    void publish(String exchange, String queue, Message message);
}
