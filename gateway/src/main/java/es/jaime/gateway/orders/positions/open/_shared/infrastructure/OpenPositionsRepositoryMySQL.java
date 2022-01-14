package es.jaime.gateway.orders.positions.open._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.*;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@DependsOn({"hibernate-configuration"})
@Transactional("gateway-transaction-manager")
public class OpenPositionsRepositoryMySQL extends HibernateRepository<OpenPosition> implements OpenPositionsRepository {
    public OpenPositionsRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, OpenPosition.class);
    }

    @Override
    public void save(OpenPosition trade) {
        persist(trade);
    }

    @Override
    public List<OpenPosition> findByClientId(OrderClientId clientId) {
        return byQuery("SELECT * FROM open_positions WHERE clientId = '"+clientId.value()+"'").get();
    }
    
    @Override
    public Optional<OpenPosition> findByPositionId(OrderId orderId) {
        return byId(orderId);
    }

    @Override
    public void deleteByPositionId(OrderId orderId) {
        delete("open_positions", "orderId = '" + orderId.value() + "'");
    }

    @Override
    protected Function<Object[], OpenPosition> queryMapper() {
        return primitives -> new OpenPosition(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderState.of(String.valueOf(primitives[3])),
                OrderTicker.of(String.valueOf(primitives[4])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(primitives[5]))),
                ExecutedOrderType.of(String.valueOf(primitives[6])),
                PositionOpeningPrice.of(Double.parseDouble(String.valueOf(primitives[7]))),
                PositionOpeningDate.of(String.valueOf(primitives[8]))
        );
    }
}
