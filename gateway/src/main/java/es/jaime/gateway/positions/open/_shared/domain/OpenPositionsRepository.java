package es.jaime.gateway.positions.open._shared.domain;

import es.jaime.gateway.positions._shared.PositionClientId;

import java.util.List;
import java.util.Optional;

public interface OpenPositionsRepository {
    void save(OpenPosition trade);

    List<OpenPosition> findByClientId(PositionClientId clientId);

    Optional<OpenPosition> findByPositionId(PositionClientId positionId);

    void deleteByPositionId(PositionClientId positionId);
}
