package es.jaime.gateway.activeorders._shared.domain;

import java.util.List;
import java.util.Optional;

public interface ActiveOrderRepository {
    void save(ActiveOrder activeOrder);

    Optional<ActiveOrder> findByOrderId(ActiveOrderID id);

    Optional<List<ActiveOrder>> findByClientId(ActiveOrderClientID clientID);
}
