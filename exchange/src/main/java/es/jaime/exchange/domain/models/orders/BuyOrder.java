package es.jaime.exchange.domain.models.orders;

import lombok.Getter;

public final class BuyOrder extends Order{
    @Getter private final String ticker;

    public BuyOrder(String orderId, String clientId, String date, double executionPrice, int quantity, String ticker) {
        super(orderId, clientId, date, executionPrice, quantity);
        this.ticker = ticker;
    }

    @Override
    public int compareTo(Order other) {
        return (int) (other.getExecutionPrice() - getExecutionPrice());
    }
}
