package es.jaime.gateway.activeorders._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderID;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional("gateway-transaction-manager")
public class ActiveOrderRepositoryMySQL extends HibernateRepository<ActiveOrder> implements ActiveOrderRepository {
    public ActiveOrderRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, ActiveOrder.class);
    }

    @Override
    public void save(ActiveOrder activeOrder) {
        persist(activeOrder);
    }

    @Override
    public Optional<ActiveOrder> findByOrderId(ActiveOrderID id) {
        return findByOrderId(id);
    }
}
