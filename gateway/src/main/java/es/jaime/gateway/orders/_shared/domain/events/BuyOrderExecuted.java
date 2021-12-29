package es.jaime.gateway.orders._shared.domain.events;


public final class BuyOrderExecuted extends OrderExecuted{
    public BuyOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                            int quantity, String date) {
        super(orderId, clientId, ticker, executionPrice, quantity, date);
    }
}
