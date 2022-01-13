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
        return primitives -> new ClosedPosition(
                OrderId.of(String.valueOf(primitives[0])),
                OrderClientId.of(String.valueOf(primitives[1])),
                OrderDate.of(String.valueOf(primitives[2])),
                OrderState.of(String.valueOf(primitives[3])),
                OrderTicker.of(String.valueOf(primitives[4])),
                OrderQuantity.of(Integer.parseInt(String.valueOf(primitives[5]))),
                ExecutedOrderType.of(String.valueOf(primitives[6])),
                PositionOpeningPrice.of(Double.parseDouble(String.valueOf(primitives[7]))),
                PositionOpeningDate.of(String.valueOf(primitives[8])),
                ClosedPositionClosingPrice.of(Double.parseDouble(String.valueOf(primitives[9]))),
                ClosedPositionClosingDate.of(String.valueOf(primitives[10]))
        );
    }
}
