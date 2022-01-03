package es.jaime.gateway.orders.ordertypes.domain;

import java.util.List;

public interface OrderTypeRepository {
    List<OrderType> findAll();
}
