package es.jaime.gateway.orders.newmodel.execution.buy._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders.newmodel._shared.domain.OrderId;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.newmodel.execution.buy._shared.domain.OrderBuy;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional("gateway-transaction-manager")
public class BuyOrderRepositoryMySQL extends HibernateRepository<OrderBuy> implements BuyOrderRepostiry {
    public BuyOrderRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, OrderBuy.class);
    }

    @Override
    public void save(OrderBuy order) {
        save(order);
    }

    @Override
    public Optional<OrderBuy> findByOrderId(OrderId id) {
        return Optional.empty();
    }
}
