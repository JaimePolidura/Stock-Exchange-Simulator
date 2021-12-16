package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.DomainEvent;
import es.jaime.exchange.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class OrderArrivedEvent extends DomainEvent {
    @Getter private final Order order;
}
