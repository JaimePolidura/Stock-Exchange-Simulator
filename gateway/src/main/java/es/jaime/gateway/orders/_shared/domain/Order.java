package es.jaime.gateway.orders._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public final class Order extends Aggregate {
    @Getter private OrderID orderId;
    @Getter private OrderTicker ticker;
    @Getter private OrderClientID clientId;
    @Getter private OrderDate date;
    @Getter private OrderQuantity quantity;
    @Getter private OrderType type;
    @Getter private OrderExecutionPrice executionPrice;

    private Order() {}
}
