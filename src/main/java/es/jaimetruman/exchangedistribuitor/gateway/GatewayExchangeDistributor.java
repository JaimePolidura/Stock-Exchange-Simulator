package es.jaimetruman.exchangedistribuitor.gateway;

import es.jaimetruman._shared.Order;
import es.jaimetruman.exchangedistribuitor._shared.RawOrder;

import java.util.List;

public interface GatewayExchangeDistributor {
    void enqueue(RawOrder order);

    List<Order> dequeueAll();
}
