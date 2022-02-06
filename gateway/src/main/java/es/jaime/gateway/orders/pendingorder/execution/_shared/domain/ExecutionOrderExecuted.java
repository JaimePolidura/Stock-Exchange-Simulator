package es.jaime.gateway.orders.pendingorder.execution._shared.domain;

import es.jaime.gateway._shared.domain.event.DomainEvent;
import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class ExecutionOrderExecuted extends DomainEvent {
    @Getter private String orderId;
    @Getter private String clientId;
    @Getter private String ticker;
    @Getter private double priceToExecute;
    @Getter private int quantity;
    @Getter private String date;
    @Getter private String newPositionId;

    public ExecutionOrderExecuted(String orderId, String clientId, String ticker, double priceToExecute,
                                  int quantity, String date, String newPositionId) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.ticker = ticker;
        this.priceToExecute = priceToExecute;
        this.quantity = quantity;
        this.date = date;
        this.newPositionId = newPositionId;
    }

    public ExecutionOrderExecuted() {}
}
