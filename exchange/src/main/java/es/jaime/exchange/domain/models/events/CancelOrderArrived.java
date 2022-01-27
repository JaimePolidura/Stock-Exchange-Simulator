package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.orders.CancelOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class CancelOrderArrived extends OrderArrived implements AsyncDomainEvent {
    @Getter private CancelOrder cancelOrder;
    @Getter private String messageId;

    public CancelOrderArrived() {}

    @Override
    public CancelOrderArrived fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");

        return new CancelOrderArrived(new CancelOrder(
                String.valueOf(body.get("orderId")),
                String.valueOf(body.get("clientId")),
                String.valueOf(body.get("orderIdToCancel")),
                String.valueOf(body.get("ticker"))
        ), String.valueOf(primitives.get("id")));
    }

    @Override
    public MessageName eventName() {
        return MessageName.NEW_ORDER_CANCEL;
    }

    @Override
    public String getOrderId() {
        return this.cancelOrder.getOrderId();
    }
}
