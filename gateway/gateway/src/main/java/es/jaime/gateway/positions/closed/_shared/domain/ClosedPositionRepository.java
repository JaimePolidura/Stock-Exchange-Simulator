package es.jaime.gateway.positions.closed._shared.domain;

import es.jaime.gateway.positions._shared.PositionClientId;
import es.jaime.gateway.positions._shared.PositionId;

import java.util.List;

public interface ClosedPositionRepository {
    void save(ClosedPosition closedPosition);

    List<ClosedPosition> findByClientId(PositionClientId clientId);
}
