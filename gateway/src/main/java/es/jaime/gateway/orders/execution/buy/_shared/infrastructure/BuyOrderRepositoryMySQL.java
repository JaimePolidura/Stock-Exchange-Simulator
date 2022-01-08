package es.jaime.gateway.orders.execution.buy._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
import es.jaime.gateway.orders.newmodel._shared.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public List<OrderBuy> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        return byQuery("SELECT * FROM buy_orders WHERE clientId = '"+state.value()+"' AND state = '"+state.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    protected Function<Object[], OrderBuy> queryMapper() {
        return primitives -> new OrderBuy(
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
