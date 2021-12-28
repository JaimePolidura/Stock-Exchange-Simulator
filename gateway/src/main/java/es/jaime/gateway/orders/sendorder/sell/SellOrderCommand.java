package es.jaime.gateway.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.trades._shared.domain.TradeId;
import lombok.Getter;

public class SellOrderCommand implements Command {
    @Getter private final OrderClientID clientID;
    @Getter private final TradeId tradeId;
    @Getter private final OrderExecutionPrice executionPrice;
    @Getter private final OrderQuantity quantity;
    @Getter private final OrderID orderID;
    @Getter private final OrderDate orderDate;

    public SellOrderCommand(String clientId, String tradeId, double executionPrice, int quantity) {
        this.clientID = OrderClientID.of(clientId);
        this.tradeId = TradeId.of(tradeId);
        this.executionPrice = OrderExecutionPrice.of(executionPrice);
        this.quantity = OrderQuantity.of(quantity);
        this.orderID = OrderID.generate();
        this.orderDate = OrderDate.now();
    }
}
