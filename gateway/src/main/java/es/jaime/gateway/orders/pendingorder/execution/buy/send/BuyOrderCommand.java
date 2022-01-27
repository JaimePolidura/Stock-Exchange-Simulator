package es.jaime.gateway.orders.pendingorder.execution.buy.send;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import lombok.Getter;

public final class BuyOrderCommand implements Command {
    @Getter private final OrderClientId clientID;
    @Getter private final OrderId orderID;
    @Getter private final OrderDate orderDate;
    @Getter private final OrderTicker ticker;
    @Getter private final OrderPriceToExecute priceToExecute;
    @Getter private final OrderQuantity quantity;

    public BuyOrderCommand(String clientId, String ticker, double priceToExecute, int quantity) {
        this.clientID = OrderClientId.of(clientId);
        this.ticker = OrderTicker.of(ticker);
        this.priceToExecute = OrderPriceToExecute.of(priceToExecute);
        this.quantity = OrderQuantity.of(quantity);
        this.orderID = OrderId.generate();
        this.orderDate = OrderDate.now();
    }
}
