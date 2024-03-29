package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class BuyOrderArrived extends OrderArrived implements AsyncDomainEvent {
    @Getter private BuyOrder buyOrder;
    @Getter private String messageId;

    public BuyOrderArrived() {}

    @Override
    public BuyOrderArrived fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");

        return new BuyOrderArrived(new BuyOrder(
                String.valueOf(body.get("orderId")),
                String.valueOf(body.get("clientId")),
                String.valueOf(body.get("date")),
                Double.parseDouble(String.valueOf(body.get("priceToExecute"))),
                Integer.parseInt(String.valueOf(body.get("quantity"))),
                String.valueOf(body.get("ticker"))
        ), String.valueOf(primitives.get("id")));
    }

    @Override
    public MessageName eventName() {
        return MessageName.NEW_ORDER_BUY;
    }

    @Override
    public String getOrderId() {
        return this.buyOrder.getOrderId();
    }
}