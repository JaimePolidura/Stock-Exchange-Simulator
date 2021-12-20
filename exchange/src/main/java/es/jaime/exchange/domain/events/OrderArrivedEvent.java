package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.models.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OrderArrivedEvent extends DomainEvent{
    @Getter private final Order order;
}
