package es.jaime.gateway.orders.ordertypes.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders.ordertypes.domain.OrderType;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional("gateway-transaction-manager")
public class OrderTypesRepostoryMySQL extends HibernateRepository<OrderType> implements OrderTypeRepository {
    private final List<OrderType> allOrderTypesCache;
    private final Map<Integer, OrderType> indexedByOrderTypeId;

    public OrderTypesRepostoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, OrderType.class);

        this.allOrderTypesCache = new ArrayList<>();
        this.indexedByOrderTypeId = new HashMap<>();
    }

    @Override
    public List<OrderType> findAll() {
        ensureCacheNotEmptyOrLoad();

        return this.allOrderTypesCache;
    }

    @Override
    public Optional<OrderType> findByOrderTypeId(OrderTypeId orderTypeId) {
        ensureCacheNotEmptyOrLoad();

        return Optional.ofNullable(this.indexedByOrderTypeId.get(orderTypeId.value()));
    }

    private void ensureCacheNotEmptyOrLoad(){
        if(allOrderTypesCache.isEmpty()){
            this.allOrderTypesCache.addAll(super.all());
            this.addOrderTypesToIndexedHashMap();
        }
    }

    private void addOrderTypesToIndexedHashMap(){
        for (OrderType orderType : this.allOrderTypesCache) {
            indexedByOrderTypeId.put(orderType.getOrderTypeId().value(), orderType);
        }
    }
}
