package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Order extends Aggregate {
    @Getter protected OrderId orderId;
    @Getter protected OrderClientId clientId;
    @Getter protected OrderDate date;
    @Getter protected OrderType type;
    @Getter protected OrderState state;
    @Getter protected OrderTicker ticker;
}
