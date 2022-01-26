package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.models.messages.MessageName;

public abstract class DomainEvent {
    public MessageName eventName(){
        return null;
    }
}
