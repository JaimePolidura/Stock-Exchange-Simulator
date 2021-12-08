package es.jaime.gateway._shared.domain.event;

import java.util.List;

public interface EventBus {
    void publish(final List<DomainEvent> events);

    void publish(DomainEvent event);
}
