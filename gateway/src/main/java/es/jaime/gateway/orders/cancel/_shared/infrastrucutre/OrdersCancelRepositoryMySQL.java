package es.jaime.gateway.orders.cancel._shared.infrastrucutre;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.orders.cancel._shared.domain.OrderCancel;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public List<OrderCancel> findByOrderStateAndClientId(OrderState orderState, OrderClientId clientId) {
        return byQuery("SELECT * FROM cancel_orders WHERE state = '"+orderState.value()+"' AND clientId = '"+clientId.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    protected Function<Object[], OrderCancel> queryMapper() {
        return primitives -> new OrderCancel(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderType.cancel(),
                OrderState.of(String.valueOf(primitives[4])),
                OrderTicker.of(String.valueOf(primitives[5])),
                OrderIdToCancel.of(String.valueOf(primitives[6]))
        );
    }
}
