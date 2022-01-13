package es.jaime.gateway.orders.positions.open._shared.domain;

import java.util.List;
import java.util.Optional;

public interface OpenPositionsRepository {
    void save(OpenPosition trade);

    List<OpenPosition> findByClientId(PositionClientId clientId);

    Optional<OpenPosition> findByPositionId(PositionId positionId);

    void deleteByPositionId(PositionId positionId);
}
