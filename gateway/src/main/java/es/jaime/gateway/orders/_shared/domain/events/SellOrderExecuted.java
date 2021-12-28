package es.jaime.gateway.orders._shared.domain.events;

import es.jaime.gateway.orders._shared.domain.OrderTypeValues;
import lombok.Getter;

public final class SellOrderExecuted extends OrderExecuted{
    @Getter private final String tradeId;

    public SellOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                             int quantity, String date, OrderTypeValues orderType, String tradeId) {
        super(orderId, clientId, ticker, executionPrice, quantity, date, orderType);
        this.tradeId = tradeId;
    }
}
