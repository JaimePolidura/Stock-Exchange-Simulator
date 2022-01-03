package es.jaime.gateway.orders.orders._shared.domain.orderbody;

import es.jaime.gateway.orders.orders._shared.domain.OrderBody;
import lombok.Getter;
import org.json.JSONObject;

import java.util.Map;

public class OrderBodyBuy extends OrderBody {
    @Getter private final String ticker;
    @Getter private final int quantity;
    @Getter private final double executionPrice;

    public OrderBodyBuy(String ticker, int quantity, double executionPrice) {
        super(new JSONObject(Map.of(
                "ticker", ticker,
                "quantity", quantity,
                "executionPrice", executionPrice
        )).toString());

        this.ticker = ticker;
        this.quantity = quantity;
        this.executionPrice = executionPrice;
    }

    public static OrderBodyBuy of(String ticker, int quantity, double executionPrice){
        return new OrderBodyBuy(ticker, quantity, executionPrice);
    }
}
