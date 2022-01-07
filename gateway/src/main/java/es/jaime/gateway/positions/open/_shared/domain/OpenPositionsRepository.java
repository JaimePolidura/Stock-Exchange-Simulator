package es.jaime.gateway.positions.open._shared.domain;

import es.jaime.gateway.positions._shared.PositionClientId;
import es.jaime.gateway.positions._shared.PositionId;

import java.util.List;
import java.util.Optional;

public interface OpenPositionsRepository {
    void save(OpenPosition trade);

    List<OpenPosition> findByClientId(PositionClientId clientId);

    Optional<OpenPosition> findByPositionId(PositionId positionId);

    void deleteByPositionId(PositionId positionId);
}
