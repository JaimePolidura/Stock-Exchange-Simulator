package es.jaime.gateway.activeorders._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.activeorders._shared.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional("gateway-transaction-manager")
public class ActiveOrderRepositoryMySQL extends HibernateRepository<ActiveOrder> implements ActiveOrderRepository {
    public ActiveOrderRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, ActiveOrder.class);
    }

    @Override
    public void save(ActiveOrder activeOrder) {
        persist(activeOrder);
    }

    @Override
    public Optional<ActiveOrder> findByOrderId(ActiveOrderID id) {
        return byId(id);
    }

    @Override
    public Optional<List<ActiveOrder>> findByClientId(ActiveOrderClientID clientID) {
        return byQuery("SELECT * FROM activeorders WHERE activeorders.clientId = '"+clientID.value()+"'");
    }

    @Override
    public void deleteByOrderId(ActiveOrderID id) {
        delete("activeorders", "activeorderId = '"+id.value()+"'");
    }

    @Override
    protected Function<Object[], ActiveOrder> queryMapper(){
        return values -> new ActiveOrder(
                ActiveOrderID.of(String.valueOf(values[0])),
                ActiveOrderTicker.of(String.valueOf(values[1])),
                ActiveOrderClientID.of(String.valueOf(values[2])),
                ActiveOrderDate.of(String.valueOf(values[3])),
                ActiveOrderQuantity.of(Integer.parseInt(String.valueOf(values[4]))),
                ActiveOrderType.of(String.valueOf(values[5])),
                ActiveOrderExecutionPrice.of(Double.parseDouble(String.valueOf(values[6]))),
                ActiveOrderStatus.of("SENDING")
        );
    }
}
