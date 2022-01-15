package es.jaime.exchange.domain.models.orders;

import lombok.Getter;

public abstract class ExecutionOrder implements Comparable<ExecutionOrder> {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final double executionPrice;
    @Getter private int quantity;
    @Getter private final String ticker;
    @Getter private int ttl = 1;

    public ExecutionOrder(String orderId, String clientId, String date, double executionPrice, int quantity, String ticker) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.date = date;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.ticker = ticker;
    }

    public int decreaseTtlByOne(){
        return --ttl;
    }

    public void decreaseQuantityBy(int quantity) {
        this.quantity = this.quantity - quantity;
    }
}
