package es.jaime.gateway.orders.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.positions._shared.PositionId;
import lombok.Getter;

public class SellOrderCommand implements Command {
    @Getter private final OrderClientId clientID;
    @Getter private final PositionId positionId;
    @Getter private final OrderId orderID;
    @Getter private final OrderDate orderDate;
    @Getter private final double executionPrice;
    @Getter private final int quantity;

    public SellOrderCommand(String clientId, String positionId, double executionPrice, int quantity) {
        this.clientID = OrderClientId.of(clientId);
        this.positionId = PositionId.of(positionId);
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.orderID = OrderId.generate();
        this.orderDate = OrderDate.now();
    }
}
