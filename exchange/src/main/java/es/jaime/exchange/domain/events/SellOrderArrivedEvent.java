package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.models.orders.SellOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class SellOrderArrivedEvent extends DomainEvent{
    @Getter private SellOrder sellOrder;

    public SellOrderArrivedEvent () {}

    @Override
    public SellOrderArrivedEvent fromPrimitives(Map<String, Object> primitives) {
        return new SellOrderArrivedEvent(new SellOrder(
                String.valueOf(primitives.get("orderId")),
                String.valueOf(primitives.get("clientId")),
                String.valueOf(primitives.get("date")),
                Double.parseDouble(String.valueOf(primitives.get("executionPrice"))),
                Integer.parseInt(String.valueOf(primitives.get("quantity"))),
                String.valueOf(primitives.get("positionId"))
        ));
    }

    @Override
    public String eventName() {
        return "new-order-sell";
    }
}
