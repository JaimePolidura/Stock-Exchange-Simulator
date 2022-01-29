package es.jaime.gateway.orders.positions.closed._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;

import java.util.List;

public interface ClosedPositionRepository {
    void save(ClosedPosition closedPosition);

    List<ClosedPosition> findByClientId(OrderClientId clientId);

    List<ClosedPosition> findBetweenDate(PositionOpeningDate openingDate, ClosedPositionClosingDate closingPrice);
}
