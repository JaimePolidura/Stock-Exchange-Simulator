package es.jaime.gateway.orders.orders._shared.domain.orderbody;

import es.jaime.gateway.orders.orders._shared.domain.OrderBody;
import es.jaime.gateway.positions._shared.PositionId;
import lombok.Getter;
import org.json.JSONObject;

import java.util.Map;

public final class OrderBodySell extends OrderBody {
    @Getter private final String tradeId;

    public OrderBodySell(PositionId positionId, int quantity, double executionPrice, String ticker) {
        super(new JSONObject(Map.of(
                "tradeId", positionId.value(),
                "quantity", quantity,
                "executionPrice", executionPrice,
                "ticker", ticker
        )).toString());

        this.tradeId = positionId.value();
    }

    public static OrderBodySell of(PositionId positionId, int quantity, double executionPrice, String ticker){
        return new OrderBodySell(positionId, quantity, executionPrice, ticker);
    }
}
