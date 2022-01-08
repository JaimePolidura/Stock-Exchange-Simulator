package es.jaime.gateway.orders.execution.buy._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrder;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional("gateway-transaction-manager")
public class BuyOrderRepositoryMySQL extends HibernateRepository<BuyOrder> implements BuyOrderRepostiry {
    public BuyOrderRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, BuyOrder.class);
    }

    @Override
    public void save(BuyOrder order) {
        super.persist(order);
    }

    @Override
    public Optional<BuyOrder> findByOrderId(OrderId id) {
        return byId(id);
    }

    @Override
    public List<BuyOrder> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        return byQuery("SELECT * FROM buy_orders WHERE clientId = '"+clientId.value()+"' AND state = '"+state.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    protected Function<Object[], BuyOrder> queryMapper() {
        return primitives -> new BuyOrder(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderType.buy(),
                OrderState.of(String.valueOf(primitives[4])),
                OrderTicker.of(String.valueOf(primitives[5])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(primitives[6]))),
                OrderExecutionPrice.of(Double.parseDouble(String.valueOf(primitives[7])))
        );
    }
}
