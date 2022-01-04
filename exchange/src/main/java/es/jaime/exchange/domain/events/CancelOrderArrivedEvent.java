package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class CancelOrderArrivedEvent extends DomainEvent{
    @Getter private CancelOrder cancelOrder;

    public CancelOrderArrivedEvent () {}

    @Override
    public CancelOrderArrivedEvent fromPrimitives(Map<String, Object> primitives) {
        return new CancelOrderArrivedEvent(new CancelOrder(
                String.valueOf(primitives.get("orderId")),
                String.valueOf(primitives.get("clientId")),
                String.valueOf(primitives.get("orderIdToCancel")),
                String.valueOf(primitives.get("ticker"))
        ));
    }

    @Override
    public String eventName() {
        return "cancel-order";
    }
}
