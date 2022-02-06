package es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderExecuted;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Map;

public final class SellOrderExecuted extends ExecutionOrderExecuted {
    @Getter private String positionId;

    public SellOrderExecuted(String orderId, String clientId, String ticker, double priceToExecute,
                             int quantity, String date, String positionId, String newPositionId) {
        super(orderId, clientId, ticker, priceToExecute, quantity, date, newPositionId);
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
                Double.parseDouble(String.valueOf(body.get("priceToExecute"))),
                Integer.parseInt(String.valueOf(body.get("quantity"))),
                String.valueOf(body.get("date")),
                String.valueOf(body.get("positionId")),
                String.valueOf(body.get("newPositionId"))
        );
    }

    @Override
    public EventName eventName() {
        return EventName.EXECUTED_SELL_ORDER;
    }
}
