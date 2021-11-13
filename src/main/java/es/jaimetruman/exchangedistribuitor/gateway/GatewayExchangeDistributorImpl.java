package es.jaimetruman.exchangedistribuitor.gateway;

import es.jaimetruman._shared.Order;
import es.jaimetruman.exchangedistribuitor._shared.RawOrder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class GatewayExchangeDistributorImpl implements GatewayExchangeDistributor {
    @Getter private final Queue<Order> orders;

    public GatewayExchangeDistributorImpl(){
        this.orders = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void enqueue(RawOrder order) {
        checkStockListedOrThrow(order.getTicker());

        this.orders.add(new Order(order));
    }

    @Override
    public List<Order> dequeueAll() {
        return new ArrayList<>(this.orders);
    }

    private void checkStockListedOrThrow(String ticker){
    }
}
