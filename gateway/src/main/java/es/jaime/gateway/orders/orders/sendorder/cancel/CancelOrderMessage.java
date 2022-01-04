package es.jaime.gateway.orders.orders.sendorder.cancel;

import es.jaime.gateway._shared.domain.messagePublisher.CommandMessage;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class CancelOrderMessage implements CommandMessage {
    private final OrderId orderId;
    private final OrderClientId clientId;
    private final OrderId orderIdToCancel;
    private final String ticker;

    @Override
    public String name() {
        return "cancel-order";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "orderId", orderId.value(),
                "clientId", clientId.value(),
                "orderIdToCancel", orderIdToCancel.value(),
                "ticker", ticker
        );
    }

    public static CancelOrderMessage create (OrderId orderId, OrderClientId clientId, OrderId orderIdToCancel, String ticker) {
        return new CancelOrderMessage(orderId, clientId, orderIdToCancel, ticker);
    }
}
