package es.jaime.gateway._shared.domain.activeorders;

import es.jaime.gateway.orders._shared.domain.OrderId;

import java.util.List;

public interface ActiveOrdersRepository {
    List<OrderId> findOrdersByExchangeName(String exchangeName);
}
