package es.jaime.exchange.domain.events;

import es.jaime.exchange.domain.models.orders.BuyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class BuyOrderArrivedEvent extends DomainEvent{
    @Getter private BuyOrder buyOrder;

    public BuyOrderArrivedEvent () {}

    @Override
    public BuyOrderArrivedEvent fromPrimitives(Map<String, Object> primitives) {
        return new BuyOrderArrivedEvent(new BuyOrder(
                String.valueOf(primitives.get("orderId")),
                String.valueOf(primitives.get("clientId")),
                String.valueOf(primitives.get("date")),
                Double.parseDouble(String.valueOf(primitives.get("executionPrice"))),
                Integer.parseInt(String.valueOf(primitives.get("quantity"))),
                String.valueOf(primitives.get("ticker"))
        ));
    }

    @Override
    public String eventName() {
        return "new-order-buy";
    }
}