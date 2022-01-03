package es.jaime.gateway.orders.orders._shared.domain.orderbody;

import es.jaime.gateway.orders.orders._shared.domain.OrderBody;
import es.jaime.gateway.trades._shared.domain.TradeId;
import lombok.Getter;
import org.json.JSONObject;

import java.util.Map;

public final class OrderBodySell extends OrderBody {
    @Getter private final String tradeId;

    public OrderBodySell(TradeId tradeId, int quantity, double executionPrice) {
        super(new JSONObject(Map.of(
                "tradeId", tradeId.value(),
                "quantity", quantity,
                "executionPrice", executionPrice
        )).toString());

        this.tradeId = tradeId.value();
    }

    public static OrderBodySell of(TradeId tradeId, int quantity, double executionPrice){
        return new OrderBodySell(tradeId, quantity, executionPrice);
    }
}
