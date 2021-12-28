package es.jaime.exchange.domain.models.orders;

import es.jaime.exchange.domain.models.OrderType;
import lombok.Getter;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class Order implements Comparable<Order> {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final double executionPrice;
    @Getter private int quantity;
    @Getter private final OrderType type;
    @Getter private int ttl = 200;

    public Order(String orderId, String clientId, String date, double executionPrice,
                 int quantity, OrderType type) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.date = date;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
        this.type = type;
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

    private int compareBuyOrder(Order other){
        return (int) (other.getExecutionPrice() - this.executionPrice);
    }

    private int compareSellOrder(Order other){
        return (int) (this.executionPrice - other.getExecutionPrice());
    }

    public int decreaseTtlByOne(){
        return --ttl;
    }

    public void decreaseQuantityBy(int quantity) {
        this.quantity = this.quantity - quantity;
    }
}
