package es.jaime.exchange.domain.models.orders;

import lombok.Getter;

public final class SellOrder extends TradeOrder {
    @Getter private final String positionId;

    public SellOrder(String orderId, String clientId, String date, double executionPrice, int quantity, String positionId) {
        super(orderId, clientId, date, executionPrice, quantity);
        this.positionId = positionId;
    }

    @Override
    public int compareTo(TradeOrder other) {
        return (int) (getExecutionPrice() - other.getExecutionPrice());
    }
}
