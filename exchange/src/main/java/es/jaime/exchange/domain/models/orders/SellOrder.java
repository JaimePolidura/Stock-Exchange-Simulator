package es.jaime.exchange.domain.models.orders;

import lombok.Getter;

public final class SellOrder extends ExecutionOrder {
    @Getter private final String positionId;

    public SellOrder(String orderId, String clientId, String date, double executionPrice, int quantity, String positionId, String ticker) {
        super(orderId, clientId, date, executionPrice, quantity, ticker);
        this.positionId = positionId;
    }

    @Override
    public int compareTo(ExecutionOrder other) {
        return (int) (getExecutionPrice() - other.getExecutionPrice());
    }
}
