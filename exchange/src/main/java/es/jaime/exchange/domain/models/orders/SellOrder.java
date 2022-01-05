package es.jaime.exchange.domain.models.orders;

import lombok.Getter;

public final class SellOrder extends TradeOrder {
    @Getter private final String tradeId;

    public SellOrder(String orderId, String clientId, String date, double executionPrice, int quantity, String tradeId) {
        super(orderId, clientId, date, executionPrice, quantity);
        this.tradeId = tradeId;
    }

    @Override
    public int compareTo(TradeOrder other) {
        return (int) (getExecutionPrice() - other.getExecutionPrice());
    }
}
