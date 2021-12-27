package es.jaime.gateway.orders._shared.domain.events;

import es.jaime.gateway.orders._shared.domain.OrderTypeValues;

public final class SellOrderExecuted extends OrderExecuted{
    public SellOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                             int quantity, String date, OrderTypeValues orderType) {
        super(orderId, clientId, ticker, executionPrice, quantity, date, orderType);
    }
}
