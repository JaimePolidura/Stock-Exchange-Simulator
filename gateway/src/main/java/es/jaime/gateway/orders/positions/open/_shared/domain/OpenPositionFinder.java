package es.jaime.gateway.orders.positions.open._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OpenPositionFinder {
    private final OpenPositionsRepository openPositions;

    public Optional<OpenPosition> find(OrderId id){
        return openPositions.findByPositionId(id);
    }

    public List<OpenPosition> findOpenByClientIdAndSince(OrderClientId clientId, PositionOpeningDate openingDate){
        return openPositions.findOpenByClientIdAndSince(clientId, openingDate);
    }

    public Optional<OpenPosition> findLastOpenPositionsByTicker(OrderTicker ticker){
        return this.openPositions.findLastOpenPositionsByTicker(ticker);
    }
}
