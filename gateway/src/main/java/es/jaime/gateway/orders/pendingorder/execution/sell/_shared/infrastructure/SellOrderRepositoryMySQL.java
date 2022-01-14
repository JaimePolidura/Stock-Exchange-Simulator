package es.jaime.gateway.orders.pendingorder.execution.sell._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.OrderPositionIdToSell;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional("gateway-transaction-manager")
public class SellOrderRepositoryMySQL extends HibernateRepository<SellOrder> implements SellOrderRepostiry {
    public SellOrderRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, SellOrder.class);
    }

    @Override
    public void save(SellOrder order) {
        super.persist(order);
    }

    @Override
    public Optional<SellOrder> findByOrderId(OrderId id) {
        return super.byId(id);
    }

    @Override
    public List<SellOrder> findByOrderClientIdAndState(OrderClientId clientId, OrderState state) {
        return byQuery("SELECT * FROM sell_orders WHERE clientId = '"+clientId.value()+"' AND state = '"+state.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    public Optional<SellOrder> findLastOrderByStateAndByTicker(OrderState orderState, OrderTicker orderTicker) {
        Optional<List<SellOrder>> result = byQuery("SELECT * FROM sell_orders WHERE state = '"+orderState.value()+"' AND ticker = '"+orderTicker.value()+"' ORDER BY date DESC LIMIT 1");

        return result.map(sellOrders -> sellOrders.get(0));
    }

    @Override
    public void deleteByOrderId(OrderId orderId) {
        delete("sell_orders", "orderId = '"+orderId.value()+"'");
    }

    @Override
    protected Function<Object[], SellOrder> queryMapper() {
        return primitives -> new SellOrder(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderState.of(String.valueOf(primitives[3])),
                OrderTicker.of(String.valueOf(primitives[4])),
                PendingOrderType.of(String.valueOf(primitives[5])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(primitives[6]))),
                OrderPriceToExecute.of(Double.parseDouble(String.valueOf(primitives[7]))),
                OrderPositionIdToSell.of(String.valueOf(primitives[8]))
        );
    }
}
