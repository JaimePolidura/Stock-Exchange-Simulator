package es.jaime.exchange.domain.models.orders;

import es.jaime.exchange.domain.models.OrderType;
import lombok.Getter;
import org.json.JSONObject;

public final class BuyOrder extends Order{
    @Getter private final String ticker;

    public BuyOrder(String orderId, String clientId, String date, double executionPrice, int quantity, OrderType type, String ticker) {
        super(orderId, clientId, date, executionPrice, quantity, type);
        this.ticker = ticker;
    }
}
