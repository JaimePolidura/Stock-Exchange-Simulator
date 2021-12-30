package es.jaime.gateway.ordertypes.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.ordertypes.domain.OrderType;
import es.jaime.gateway.ordertypes.domain.OrderTypeRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional("gateway-transaction-manager")
public class OrderTypeRepositoryMySQL extends HibernateRepository<OrderType> implements OrderTypeRepository {
    private List<OrderType> cache;

    public OrderTypeRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, OrderType.class);

        this.cache = new ArrayList<>();
    }

    @Override
    public List<OrderType> findAll() {
        if(cache.isEmpty())
            this.cache = new ArrayList<>(super.all());

        return this.cache;

    }
}
