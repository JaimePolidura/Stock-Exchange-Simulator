package es.jaime.gateway.orders._shared.domain.events;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import es.jaime.gateway.orders._shared.domain.OrderTypeValues;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class OrderExecuted extends DomainEvent {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String ticker;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String date;
    @Getter private final OrderTypeValues orderType;
}
