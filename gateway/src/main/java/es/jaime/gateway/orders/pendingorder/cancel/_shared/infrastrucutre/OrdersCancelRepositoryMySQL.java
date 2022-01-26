package es.jaime.gateway.orders.pendingorder.cancel._shared.infrastrucutre;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderIdToCancel;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

//@Repository
//@Transactional("gateway-transaction-manager")
public class OrdersCancelRepositoryMySQL extends HibernateRepository<CancelOrder> implements OrdersCancelRepository {
    public OrdersCancelRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, CancelOrder.class);
    }

    @Override
    public void save(CancelOrder orderCancel) {
        super.persist(orderCancel);
    }

    @Override
    public Optional<CancelOrder> findById(OrderId orderId) {
        return byId(orderId);
    }

    @Override
    public List<CancelOrder> findByClientIdAndOrderState(OrderClientId clientId, OrderState orderState) {
        return byQuery("SELECT * FROM cancel_orders WHERE state = '"+orderState.value()+"' AND clientId = '"+clientId.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    public List<CancelOrder> findByTicker(OrderTicker orderTicker) {
        return byQuery("SELECT * FROM cancel_orders WHERE ticker = '"+orderTicker.value()+"'")
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    protected Function<Object[], CancelOrder> queryMapper() {
        return primitives -> new CancelOrder(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderState.of(String.valueOf(primitives[3])),
                OrderTicker.of(String.valueOf(primitives[4])),
                PendingOrderType.of(String.valueOf(primitives[5])),
                OrderIdToCancel.of(String.valueOf(primitives[6]))
        );
    }
}
