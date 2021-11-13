package es.jaimetruman.exchanges.orderexecutor;

import es.jaimetruman._shared.Order;

public interface OrderExecutor {
    void execute(Order buyOrder, Order sellOrder);
}
