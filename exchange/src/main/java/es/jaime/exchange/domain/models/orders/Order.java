package es.jaime.exchange.domain.models.orders;

import lombok.Getter;

public abstract class Order implements Comparable<Order> {
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final double executionPrice;
    @Getter private int quantity;
    @Getter private int ttl = 200;

    public Order(String orderId, String clientId, String date, double executionPrice, int quantity) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.date = date;
        this.executionPrice = executionPrice;
        this.quantity = quantity;
    }

    public int decreaseTtlByOne(){
        return --ttl;
    }

    public void decreaseQuantityBy(int quantity) {
        this.quantity = this.quantity - quantity;
    }
}
