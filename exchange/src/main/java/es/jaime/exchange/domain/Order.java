package es.jaime.exchange.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public final class Order implements Comparable<Order>{
    @Getter private final String orderId;
    @Getter private final String clientId;
    @Getter private final String date;
    @Getter private final double executionPrice;
    @Getter private final int quantity;
    @Getter private final String ticker;
    @Getter private final OrderType type;
    @Getter private int ttl = 200;

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

    private int compareBuyOrder(Order other){
        return (int) (other.getExecutionPrice() - this.executionPrice);
    }

    private int compareSellOrder(Order other){
        return (int) (this.executionPrice - other.getExecutionPrice());
    }
}
