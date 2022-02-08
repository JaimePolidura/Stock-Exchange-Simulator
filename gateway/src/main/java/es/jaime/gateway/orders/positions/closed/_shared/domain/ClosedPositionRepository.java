package es.jaime.gateway.orders.positions.closed._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionRepository;

import java.util.List;

public interface ClosedPositionRepository extends PositionRepository<ClosedPosition> {

    List<ClosedPosition> findByClientIdAndOpeningClosingDateBetween(OrderClientId clientId, PositionOpeningDate openingDate);
}
