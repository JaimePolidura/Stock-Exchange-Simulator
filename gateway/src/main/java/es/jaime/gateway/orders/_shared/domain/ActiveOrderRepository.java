package es.jaime.gateway.orders._shared.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface ActiveOrderRepository {
    void save(ActiveOrder activeOrder);
}
