package es.jaime.gateway.orders.newmodel.cancel.send;

import es.jaime.gateway._shared.domain.messages.CommandMessage;
import es.jaime.gateway._shared.domain.messages.MessageNames;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderClientId;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderTicker;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class CancelOrderMessage implements CommandMessage {
    private final OrderId orderId;
    private final OrderClientId clientId;
    private final OrderId orderIdToCancel;
    private final OrderTicker ticker;

    @Override
    public MessageNames name() {
        return MessageNames.ORDER_CANCEL;
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "orderId", orderId.value(),
                "clientId", clientId.value(),
                "orderIdToCancel", orderIdToCancel.value(),
                "ticker", ticker.value()
        );
    }

    public static CancelOrderMessage create (OrderId orderId, OrderClientId clientId, OrderId orderIdToCancel, OrderTicker ticker) {
        return new CancelOrderMessage(orderId, clientId, orderIdToCancel, ticker);
    }
}
