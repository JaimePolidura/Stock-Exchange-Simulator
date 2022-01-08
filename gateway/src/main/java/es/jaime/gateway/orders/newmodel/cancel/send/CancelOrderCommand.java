package es.jaime.gateway.orders.newmodel.cancel.send;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderClientId;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import lombok.Getter;

public class CancelOrderCommand implements Command {
    @Getter private final OrderId orderIdToCancel;
    @Getter private final OrderClientId clientId;
    @Getter private final OrderId orderId;

    public CancelOrderCommand(String orderId, String clientId) {
        this.orderIdToCancel = OrderId.of(orderId);
        this.clientId = OrderClientId.of(clientId);
        this.orderId = OrderId.generate();
    }
}
