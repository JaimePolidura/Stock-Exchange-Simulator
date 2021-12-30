package es.jaime.gateway.closedpositions._shared.domain;

import java.util.List;

public interface ClosedPositionRepository {
    void save(ClosedPosition closedPosition);

    List<ClosedPosition> findByClientId(ClosedPositionClientId clientId);
}
