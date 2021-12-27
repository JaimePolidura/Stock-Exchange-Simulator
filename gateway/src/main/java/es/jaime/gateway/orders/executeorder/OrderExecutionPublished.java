package es.jaime.gateway.orders.executeorder;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import es.jaime.gateway.orders._shared.domain.*;
import lombok.Getter;

import java.time.LocalDateTime;

public final class OrderExecutionPublished extends DomainEvent {
    @Getter private final String orderID;
    @Getter private final String clientID;
    @Getter private final LocalDateTime date;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String ticker;
    @Getter private final OrderType type;

    public OrderExecutionPublished(ExecuteOrderCommand command){
        this.orderID = command.getOrderID().value();
        this.clientID = command.getClientID().value();
        this.date = LocalDateTime.parse(command.getDate().value());
        this.executionPrice = command.getExecutionPrice().value();
        this.quantity = command.getQuantity().value();
        this.ticker = command.getTicker().value();
        this.type = command.getType();
    }
}