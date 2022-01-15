package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class ExecutionOrderExecuted extends DomainEvent {
    @Getter private String orderId;
    @Getter private String clientId;
    @Getter private String ticker;
    @Getter private double executionPrice;
    @Getter private int quantity;
    @Getter private String date;

    public ExecutionOrderExecuted(String orderId, String clientId, String ticker, double executionPrice, int quantity, String date) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.ticker = ticker;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.date = date;
    }

    public ExecutionOrderExecuted() {}
}
