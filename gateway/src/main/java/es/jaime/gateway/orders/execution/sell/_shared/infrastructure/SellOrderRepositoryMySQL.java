package es.jaime.gateway.orders.execution.sell._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
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

    @Override
    public List<OrderSell> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        return byQuery("SELECT * FROM sell_orders WHERE clientId = '"+clientId.value()+"' AND state = '"+state.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    protected Function<Object[], OrderSell> queryMapper() {
        return primitives -> new OrderSell(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderType.buy(),
                OrderState.of(String.valueOf(primitives[4])),
                OrderTicker.of(String.valueOf(primitives[5])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(primitives[6]))),
                OrderExecutionPrice.of(Double.parseDouble(String.valueOf(primitives[7]))),
                OrderPositionIdToSell.of(String.valueOf(primitives[8]))
        );
    }
}
