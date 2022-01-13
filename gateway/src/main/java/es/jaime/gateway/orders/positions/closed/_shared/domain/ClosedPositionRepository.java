package es.jaime.gateway.orders.positions.closed._shared.domain;

import java.util.List;

public interface ClosedPositionRepository {
    void save(ClosedPosition closedPosition);

    List<ClosedPosition> findByClientId(PositionClientId clientId);
}
