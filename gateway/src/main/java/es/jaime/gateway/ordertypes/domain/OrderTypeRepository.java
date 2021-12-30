package es.jaime.gateway.ordertypes.domain;

import java.util.List;

public interface OrderTypeRepository {
    List<OrderType> findAll();
}
