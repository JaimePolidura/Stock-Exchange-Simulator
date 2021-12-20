package es.jaime.exchange.domain.events;

import java.util.List;

public interface EventBus {
    void publish(List<DomainEvent> events);

    void publish(DomainEvent event);
}
