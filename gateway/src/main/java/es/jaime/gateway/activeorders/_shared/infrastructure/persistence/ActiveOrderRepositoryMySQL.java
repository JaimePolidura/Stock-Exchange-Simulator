package es.jaime.gateway.activeorders._shared.infrastructure.persistence;

import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveOrderRepositoryMySQL implements ActiveOrderRepository {
    @Override
    public void save(ActiveOrder activeOrder) {

    }
}
