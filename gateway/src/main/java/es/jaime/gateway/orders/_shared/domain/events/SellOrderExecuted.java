package es.jaime.gateway.orders._shared.domain.events;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Map;

public final class SellOrderExecuted extends OrderExecuted{
    @Getter private String tradeId;

    public SellOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                             int quantity, String date, String tradeId) {
        super(orderId, clientId, ticker, executionPrice, quantity, date);
        this.tradeId = tradeId;
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
                String.valueOf(body.get("tradeId"))
        );
    }

    @Override
    public String eventName() {
        return "order-executed-sell";
    }
}
