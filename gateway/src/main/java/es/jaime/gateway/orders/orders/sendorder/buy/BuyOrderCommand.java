package es.jaime.gateway.orders.orders.sendorder.buy;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import lombok.Getter;

public final class BuyOrderCommand implements Command {
    @Getter private final OrderClientId clientID;
    @Getter private final OrderId orderID;
    @Getter private final OrderDate orderDate;
    @Getter private final String ticker;
    @Getter private final double executionPrice;
    @Getter private final int quantity;

    public BuyOrderCommand(String clientId, String ticker, double executionPrice, int quantity) {
        this.clientID = OrderClientId.of(clientId);
        this.ticker = ticker;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.orderID = OrderId.generate();
        this.orderDate = OrderDate.now();
    }
}
