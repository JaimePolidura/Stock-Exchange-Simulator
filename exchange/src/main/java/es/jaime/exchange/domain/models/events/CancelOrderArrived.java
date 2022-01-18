package es.jaime.exchange.domain.models.events;

import es.jaime.exchange.domain.models.orders.CancelOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class CancelOrderArrived extends OrderArrived {
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
    public String messageId() {
        return this.messageId;
    }

    @Override
    public String eventName() {
        return "cancel-order";
    }

    @Override
    public String getOrderId() {
        return this.cancelOrder.getOrderId();
    }
}
