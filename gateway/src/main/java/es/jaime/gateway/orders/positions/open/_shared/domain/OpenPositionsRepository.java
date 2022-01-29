package es.jaime.gateway.orders.positions.open._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;

import java.util.List;
import java.util.Optional;

public interface OpenPositionsRepository {
    void save(OpenPosition trade);

    List<OpenPosition> findByClientId(OrderClientId clientId);

    List<OpenPosition> findOpenByClientIdAndSince(OrderClientId clientId, PositionOpeningDate openingDate);

    Optional<OpenPosition> findByPositionId(OrderId positionId);

    void deleteByPositionId(OrderId positionId);
}
