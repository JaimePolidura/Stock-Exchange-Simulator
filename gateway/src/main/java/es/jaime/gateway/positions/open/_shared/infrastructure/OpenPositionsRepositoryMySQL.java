package es.jaime.gateway.positions.open._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.positions._shared.*;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionsRepository;
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
    public List<OpenPosition> findByClientId(PositionClientId clientId) {
        return byQuery("SELECT * FROM open_positions WHERE clientId = '"+clientId.value()+"'").get();
    }

    @Override
    public Optional<OpenPosition> findByPositionId(PositionClientId tradeId) {
        return Optional.empty();
    }

    @Override
    public void deleteByPositionId(PositionClientId positionId) {
        delete("open_positions", "positionId = '" + positionId.value() + "'");
    }
    @Override
    protected Function<Object[], OpenPosition> queryMapper() {
        return primitives -> new OpenPosition(
                PositionId.of(String.valueOf(primitives[0])),
                PositionClientId.from(String.valueOf(primitives[1])),
                PositionTicker.of(String.valueOf(primitives[2])),
                PositionOpeningPrice.of(Double.parseDouble(String.valueOf(primitives[3]))),
                PositionOpeningDate.of(String.valueOf(primitives[4])),
                PositionQuantity.of(Integer.parseInt(String.valueOf(primitives[5])))
        );
    }
}
