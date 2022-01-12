package es.jaime.gateway.orders.execution.sell._shared.domain;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway.orders.execution._shared.domain.OrderExecuted;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

public final class SellOrderExecuted extends OrderExecuted {
    @Getter private
    String positionId;

    public SellOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                             int quantity, String date, String positionId) {
        super(orderId, clientId, ticker, executionPrice, quantity, date);
        this.positionId = positionId;
    }

    public SellOrderExecuted () {}

    @Override
    public SellOrderExecuted fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");
        Map<String, Object> meta = (Map<String, Object>) primitives.get("meta");

        return new SellOrderExecuted(
                String.valueOf(body.get("orderId")),
                String.valueOf(((ArrayList<String>) meta.get("to")).get(0)),
                String.valueOf(body.get("ticker")),
                Double.parseDouble(String.valueOf(body.get("executionPrice"))),
                Integer.parseInt(String.valueOf(body.get("quantity"))),
                String.valueOf(body.get("date")),
                String.valueOf(body.get("positionId"))
        );
    }

    @Override
    public EventName eventName() {
        return EventName.EXECUTED_SELL_ORDER;
    }
}
