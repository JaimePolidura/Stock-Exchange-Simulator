package es.jaime.gateway.orders.execution.sell.send;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.positions._shared.PositionId;
import lombok.Getter;

public class SellOrderCommand implements Command {
    @Getter private final OrderClientId clientID;
    @Getter private final PositionId positionId;
    @Getter private final OrderId orderID;
    @Getter private final OrderDate orderDate;
    @Getter private final OrderExecutionPrice executionPrice;
    @Getter private final OrderQuantity quantity;

    public SellOrderCommand(String clientId, String positionId, double executionPrice, int quantity) {
        this.clientID = OrderClientId.of(clientId);
        this.positionId = PositionId.of(positionId);
        this.executionPrice = OrderExecutionPrice.of(executionPrice);
        this.quantity = OrderQuantity.of(quantity);
        this.orderID = OrderId.generate();
        this.orderDate = OrderDate.now();
    }
}
