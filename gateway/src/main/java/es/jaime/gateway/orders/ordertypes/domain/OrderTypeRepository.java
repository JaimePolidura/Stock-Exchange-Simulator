package es.jaime.gateway.orders.ordertypes.domain;

import java.util.List;
import java.util.Optional;

public interface OrderTypeRepository {
    List<OrderType> findAll();

    Optional<OrderType> findByOrderTypeId(OrderTypeId orderTypeId);
}
