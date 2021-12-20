package es.jaime.exchange.domain;

import lombok.Getter;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public final class Order implements Comparable<Order> {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final double executionPrice;
    @Getter private int quantity;
    @Getter private final String ticker;
    @Getter private final OrderType type;
    @Getter private int ttl = 200;

    public Order(String orderId, String clientId, String date, double executionPrice,
                 int quantity, String ticker, OrderType type) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.date = date;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.ticker = ticker;
        this.type = type;
    }

    @SneakyThrows
    public static Order create(JSONObject jsonObject) {
        return new Order(
                (String) jsonObject.get("orderId"),
                (String) jsonObject.get("clientId"),
                (String) jsonObject.get("date"),
                Double.parseDouble(String.valueOf(jsonObject.get("executionPrice"))),
                Integer.parseInt(String.valueOf(jsonObject.get("quantity"))),
                (String) jsonObject.get("ticker"),
                OrderType.valueOf(jsonObject.get("type").toString())
        );
    }

    @Override
    public int compareTo(Order other) {
        if(this.type == OrderType.BUY && other.type == OrderType.BUY){
            return compareBuyOrder(other);
        }
        if(this.type == OrderType.SELL && other.type == OrderType.SELL){
            return compareSellOrder(other);
        }

        //THIS shouldn't happen because we are storing buy & sell orders in different queues
        return 0;
    }

    public int decreaseTtlByOne(){
        return --ttl;
    }

    public void decreaseQuantityBy(int quantity) {
        this.quantity = this.quantity - quantity;
    }

    private int compareBuyOrder(Order other){
        return (int) (other.getExecutionPrice() - this.executionPrice);
    }

    private int compareSellOrder(Order other){
        return (int) (this.executionPrice - other.getExecutionPrice());
    }
    
    public Map<String, Object> toPrimitives(){
        return new HashMap<>() {{
            put("orderId", orderId);
            put("clientId", clientId);
            put("date", date);
            put("executionPrice", executionPrice);
            put("quantity", quantity);
            put("ticker", ticker);
            put("type", type.toString());
        }};
    }
}
