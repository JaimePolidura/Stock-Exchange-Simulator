package es.jaime.gateway.activeorders.executeorder;

import es.jaime.gateway._shared.domain.bus.event.DomainEvent;
import es.jaime.gateway.activeorders._shared.domain.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public final class OrderExecutionPublished extends DomainEvent {
    @Getter private final String orderID;
    @Getter private final String clientID;
    @Getter private final LocalDateTime date;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String ticker;
    @Getter private final ActiveOrderType type;

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
