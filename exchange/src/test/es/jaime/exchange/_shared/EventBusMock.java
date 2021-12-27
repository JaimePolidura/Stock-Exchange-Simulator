package es.jaime.exchange._shared;

import es.jaime.exchange.domain.events.DomainEvent;
import es.jaime.exchange.domain.events.EventBus;

import java.util.List;

public class EventBusMock implements EventBus {
    @Override
    public void publish(List<DomainEvent> events) {
    }

    @Override
    public void publish(DomainEvent event) {
    }
}
