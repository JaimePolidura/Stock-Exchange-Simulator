package es.jaime.gateway.orders.newmodel.execution.sell._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.newmodel.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional("gateway-transaction-manager")
public class SellOrderRepositoryMySQL extends HibernateRepository<OrderSell> implements SellOrderRepostiry {
    public SellOrderRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, OrderSell.class);
    }

    @Override
    public void save(OrderSell order) {
        save(order);
    }

    @Override
    public Optional<OrderSell> findByOrderId(OrderId id) {
        return Optional.empty();
    }
}
