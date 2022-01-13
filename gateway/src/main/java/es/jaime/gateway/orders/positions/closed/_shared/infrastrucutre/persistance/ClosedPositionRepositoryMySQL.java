package es.jaime.gateway.orders.positions.closed._shared.infrastrucutre.persistance;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.positions._shared.*;
import es.jaime.gateway.orders.positions.closed._shared.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Transactional("gateway-transaction-manager")
@Repository
public class ClosedPositionRepositoryMySQL extends HibernateRepository<ClosedPosition> implements ClosedPositionRepository {
    public ClosedPositionRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, ClosedPosition.class);
    }

    @Override
    public void save(ClosedPosition closedPosition) {
        persist(closedPosition);
    }

    @Override
    public List<ClosedPosition> findByClientId(OrderClientId clientId) {
        return byQuery("SELECT * FROM closed_positions WHERE clientId = '"+clientId.value()+"'").get();
    }

    @Override
    protected Function<Object[], ClosedPosition> queryMapper() {
        return objects -> new ClosedPosition(
                OrderId.of(String.valueOf(objects[0])),
                OrderClientId.of(String.valueOf(objects[1])),
                OrderDate.of(String.valueOf(objects[2])),
                OrderTicker.of(String.valueOf(objects[3])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(objects[4]))),
                PositionOpeningPrice.of(Double.parseDouble(String.valueOf(objects[5]))),
                PositionOpeningDate.of(String.valueOf(objects[6])),
                ClosedPositionClosingPrice.of(Double.parseDouble(String.valueOf(objects[7]))),
                ClosedPositionClosingDate.from(String.valueOf(objects[8])),
                ExecutedOrderType.of(String.valueOf(objects[9])),
                OrderState.of(String.valueOf(objects[10]))
        );
    }
}
