package es.jaime.gateway.orders.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Order extends Aggregate {
    @Getter private OrderId orderId;
    @Getter private OrderClientId clientId;
    @Getter private OrderDate date;
    @Getter private OrderTypeId orderTypeId;
    @Getter private OrderBody body;

    private Order() {}
}
