package es.jaime.gateway.orders.sendorder.buy;

import com.google.common.eventbus.EventBus;
import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;

public final class BuyOrderCommand implements Command {
    @Getter private final OrderClientID clientID;
    @Getter private final OrderTicker ticker;
    @Getter private final OrderExecutionPrice executionPrice;
    @Getter private final OrderQuantity quantity;
    @Getter private final OrderID orderID;
    @Getter private final OrderDate orderDate;

    public BuyOrderCommand(String clientId, String ticker, double executionPrice, int quantity) {
        this.clientID = OrderClientID.of(clientId);
        this.ticker = OrderTicker.of(ticker);
        this.executionPrice = OrderExecutionPrice.of(executionPrice);
        this.quantity = OrderQuantity.of(quantity);
        this.orderID = OrderID.generate();
        this.orderDate = OrderDate.now();
    }
}
