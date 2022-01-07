package es.jaime.gateway.orders.orders._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders.orders._shared.domain.*;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
        super.persist(order);
    }

    @Override
    public Optional<Order> findByOrderId(OrderId id) {
        return byId(id);
    }

    @Override
    public List<Order> findOrdersByTypeAndClientId(OrderTypeId orderTypeId, OrderClientId clientId) {
        return byQuery("SELECT * FROM orders WHERE orders.clientId = '"+clientId.value()+"' AND orders.orderTypeId = " + orderTypeId.value())
                .orElse(Collections.EMPTY_LIST);
    }

    @Override
    public void deleteByOrderId(OrderId id) {
        delete("orders", "orderId = '"+id.value()+"'");
    }

    @Override
    protected Function<Object[], Order> queryMapper(){
        return values -> new Order(
                OrderId.of(String.valueOf(values[0])),
                OrderClientId.of(String.valueOf(values[1])),
                OrderDate.of(String.valueOf(values[2])),
                OrderTypeId.of(Integer.parseInt(String.valueOf(values[3]))),
                OrderBody.of(toMap(String.valueOf(values[4])))
        );
    }

    private Map<String, Object> toMap(String stringValue){
        return (new JSONObject(stringValue)).toMap();
    }
}
