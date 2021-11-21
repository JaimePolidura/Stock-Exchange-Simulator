package es.jaime.gateway.activeorders._shared.domain;

import java.util.Optional;

public interface ActiveOrderRepository {
    void save(ActiveOrder activeOrder);

    Optional<ActiveOrder> findByOrderId(ActiveOrderID id);
}
