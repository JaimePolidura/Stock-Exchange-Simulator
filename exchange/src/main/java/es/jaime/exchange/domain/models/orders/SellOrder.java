package es.jaime.exchange.domain.models.orders;

import es.jaime.exchange.domain.models.OrderType;
import lombok.Getter;

public final class SellOrder extends Order{
    @Getter private final String tradeId;

    public SellOrder(String orderId, String clientId, String date, double executionPrice, int quantity, OrderType type, String tradeId) {
        super(orderId, clientId, date, executionPrice, quantity, type);
        this.tradeId = tradeId;
    }
}
