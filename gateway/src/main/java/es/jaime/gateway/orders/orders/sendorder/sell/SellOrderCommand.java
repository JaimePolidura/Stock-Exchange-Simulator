package es.jaime.gateway.orders.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.trades._shared.domain.TradeId;
import lombok.Getter;

public class SellOrderCommand implements Command {
    @Getter private final OrderClientId clientID;
    @Getter private final TradeId tradeId;
    @Getter private final OrderId orderID;
    @Getter private final OrderDate orderDate;
    @Getter private final double executionPrice;
    @Getter private final int quantity;

    public SellOrderCommand(String clientId, String tradeId, double executionPrice, int quantity) {
        this.clientID = OrderClientId.of(clientId);
        this.tradeId = TradeId.of(tradeId);
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.orderID = OrderId.generate();
        this.orderDate = OrderDate.now();
    }
}
