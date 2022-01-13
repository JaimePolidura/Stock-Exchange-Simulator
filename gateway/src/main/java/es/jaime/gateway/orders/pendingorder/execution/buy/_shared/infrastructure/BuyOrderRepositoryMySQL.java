package es.jaime.gateway.orders.pendingorder.execution.buy._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional("gateway-transaction-manager")
public class BuyOrderRepositoryMySQL extends HibernateRepository<BuyOrder> implements BuyOrderRepostory {
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
    public Optional<BuyOrder> findLastOrderByStateAndByTicker(OrderState orderState, OrderTicker orderTicker) {
        Optional<List<BuyOrder>> result = byQuery("SELECT * FROM buy_orders WHERE state = '"+orderState.value()+"' AND ticker = '"+orderTicker.value()+"' ORDER BY date DESC LIMIT 1");

        return result.map(buyOrders -> buyOrders.get(0));
    }

    @Override
    protected Function<Object[], BuyOrder> queryMapper() {
        return primitives -> new BuyOrder(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderState.of(String.valueOf(primitives[3])),
                OrderTicker.of(String.valueOf(primitives[4])),
                PendingOrderType.of(String.valueOf(primitives[5])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(primitives[6]))),
                OrderPriceToExecute.of(Double.parseDouble(String.valueOf(primitives[7])))
        );
    }
}
