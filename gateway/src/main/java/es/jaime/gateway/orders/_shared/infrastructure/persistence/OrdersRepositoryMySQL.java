package es.jaime.gateway.orders._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional("gateway-transaction-manager")
public class OrdersRepositoryMySQL extends HibernateRepository<Order> implements OrdersRepository {
    public OrdersRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, Order.class);
    }

    @Override
    public void save(Order order) {
        persist(order);
    }

    @Override
    public Optional<Order> findByOrderId(OrderID id) {
        return byId(id);
    }

    @Override
    public Optional<List<Order>> findByClientId(OrderClientID clientID) {
        return byQuery("SELECT * FROM orders WHERE orders.clientId = '"+clientID.value()+"'");
    }

    @Override
    public void deleteByOrderId(OrderID id) {
        delete("orders", "orderId = '"+id.value()+"'");
    }

    @Override
    protected Function<Object[], Order> queryMapper(){
        return values -> new Order(
                OrderID.of(String.valueOf(values[0])),
                OrderTicker.of(String.valueOf(values[1])),
                OrderClientID.of(String.valueOf(values[2])),
                OrderDate.of(String.valueOf(values[3])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(values[4]))),
                OrderType.of(String.valueOf(values[5])),
                OrderExecutionPrice.of(Double.parseDouble(String.valueOf(values[6]))),
                OrderStatus.of("SENDING")
        );
    }
}
