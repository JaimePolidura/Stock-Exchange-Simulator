package es.jaime.gateway.orders.newmodel.cancel._shared.infrastrucutre;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders.newmodel._shared.OrderId;
import es.jaime.gateway.orders.newmodel.cancel._shared.domain.OrderCancel;
import es.jaime.gateway.orders.newmodel.cancel._shared.domain.OrdersCancelRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional("gateway-transaction-manager")
public class OrdersCancelRepositoryMySQL extends HibernateRepository<OrderCancel> implements OrdersCancelRepository {
    public OrdersCancelRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, OrderCancel.class);
    }

    @Override
    public void save(OrderCancel orderCancel) {
        save(orderCancel);
    }

    @Override
    public Optional<OrderCancel> findById(OrderId orderId) {
        return byId(orderId);
    }
}
