package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.orders.SellOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class SellOrderArrived extends OrderArrived implements AsyncDomainEvent {
    @Getter private SellOrder sellOrder;
    @Getter private String messageId;

    public SellOrderArrived() {}

    @Override
    public SellOrderArrived fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");

        return new SellOrderArrived(new SellOrder(
                String.valueOf(body.get("orderId")),
                String.valueOf(body.get("clientId")),
                String.valueOf(body.get("date")),
                Double.parseDouble(String.valueOf(body.get("priceToExecute"))),
                Integer.parseInt(String.valueOf(body.get("quantity"))),
                String.valueOf(body.get("positionId")),
                String.valueOf(body.get("ticker"))
        ), (String) primitives.get("id"));
    }

    @Override
    public MessageName eventName() {
        return MessageName.NEW_ORDER_SELL;
    }

    @Override
    public String getOrderId() {
        return this.sellOrder.getOrderId();
    }
}
