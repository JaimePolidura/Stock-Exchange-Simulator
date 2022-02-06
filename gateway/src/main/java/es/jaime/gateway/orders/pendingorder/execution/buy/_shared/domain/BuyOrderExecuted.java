package es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain;



import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderExecuted;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@ToString
public final class BuyOrderExecuted extends ExecutionOrderExecuted {
    public BuyOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                            int quantity, String date, String newPositionId) {
        super(orderId, clientId, ticker, executionPrice, quantity, date, newPositionId);
    }

    public BuyOrderExecuted () {}

    @Override
    public BuyOrderExecuted fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");
        Map<String, Object> meta = (Map<String, Object>) primitives.get("meta");

        return new BuyOrderExecuted(
                String.valueOf(body.get("orderId")),
                String.valueOf(((ArrayList<String>) meta.get("to")).get(0)),
                String.valueOf(body.get("ticker")),
                Double.parseDouble(String.valueOf(body.get("priceToExecute"))),
                Integer.parseInt(String.valueOf(body.get("quantity"))),
                String.valueOf(body.get("date")),
                String.valueOf(body.get("newPositionId"))
        );
    }

    @Override
    public EventName eventName() {
        return EventName.EXECUTED_BUY_ORDER;
    }
}
