package es.jaime.gateway.orders.pendingorder.execution.sell.send;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import lombok.Getter;

public class SellOrderCommand implements Command {
    @Getter private final OrderClientId clientID;
    @Getter private final PositionId positionId;
    @Getter private final OrderId orderID;
    @Getter private final OrderDate orderDate;
    @Getter private final OrderPriceToExecute executionPrice;
    @Getter private final OrderQuantity quantity;

    public SellOrderCommand(String clientId, String positionId, double executionPrice, int quantity) {
        this.clientID = OrderClientId.of(clientId);
        this.positionId = PositionId.of(positionId);
        this.executionPrice = OrderPriceToExecute.of(executionPrice);
        this.quantity = OrderQuantity.of(quantity);
        this.orderID = OrderId.generate();
        this.orderDate = OrderDate.now();
    }
}
