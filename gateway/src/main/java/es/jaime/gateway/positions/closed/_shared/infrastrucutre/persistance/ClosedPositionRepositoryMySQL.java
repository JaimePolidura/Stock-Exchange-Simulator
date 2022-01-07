package es.jaime.gateway.positions.closed._shared.infrastrucutre.persistance;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.positions._shared.*;
import es.jaime.gateway.positions.closed._shared.domain.*;
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
    public List<ClosedPosition> findByClientId(PositionClientId clientId) {
        return byQuery("SELECT * FROM closed_positions WHERE clientId = '"+clientId.value()+"'").get();
    }

    @Override
    protected Function<Object[], ClosedPosition> queryMapper() {
        return objects -> new ClosedPosition(
                PositionId.of(String.valueOf(objects[0])),
                PositionClientId.from(String.valueOf(objects[1])),
                PositionTicker.of(String.valueOf(objects[2])),
                PositionQuantity.of(Integer.parseInt(String.valueOf(objects[3]))),
                PositionOpeningPrice.of(Double.parseDouble(String.valueOf(objects[4]))),
                PositionOpeningDate.of(String.valueOf(objects[5])),
                ClosedPositionClosingPrice.from(Double.parseDouble(String.valueOf(objects[6]))),
                ClosedPositionClosingDate.from(String.valueOf(objects[7]))
        );
    }
}
