package es.jaime.gateway.closedpositions._shared.infrastrucutre.persistance;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.closedpositions._shared.domain.*;
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
    public List<ClosedPosition> findByClientId(ClosedPositionClientId clientId) {
        return byQuery("SELECT * FROM closedpositions WHERE clientId = '"+clientId.value()+"'").get();
    }

    @Override
    protected Function<Object[], ClosedPosition> queryMapper() {
        return objects -> new ClosedPosition(
                ClosedPositionId.from(String.valueOf(objects[0])),
                ClosedPositionClientId.from(String.valueOf(objects[1])),
                ClosedPositionTicker.from(String.valueOf(objects[2])),
                ClosedPositionQuantity.from(Integer.parseInt(String.valueOf(objects[3]))),
                ClosedPositionOpeningPrice.from(Double.parseDouble(String.valueOf(objects[4]))),
                ClosedPositionOpeningDate.from(String.valueOf(objects[5])),
                ClosedPositionClosingPrice.from(Double.parseDouble(String.valueOf(objects[6]))),
                ClosedPositionClosingDate.from(String.valueOf(objects[7]))
        );
    }
}
