package es.jaime.gateway.orders._shared.domain.events;



import java.util.ArrayList;
import java.util.Map;

public final class BuyOrderExecuted extends OrderExecuted{
    public BuyOrderExecuted(String orderId, String clientId, String ticker, double executionPrice,
                            int quantity, String date) {
        super(orderId, clientId, ticker, executionPrice, quantity, date);
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
                Double.parseDouble(String.valueOf(body.get("executionPrice"))),
                Integer.parseInt(String.valueOf(body.get("quantity"))),
                String.valueOf(body.get("date"))
        );
    }

    @Override
    public String eventName() {
        return "order-executed-buy";
    }
}
