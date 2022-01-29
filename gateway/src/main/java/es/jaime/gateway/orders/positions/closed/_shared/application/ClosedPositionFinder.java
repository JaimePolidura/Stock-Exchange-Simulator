package es.jaime.gateway.orders.positions.closed._shared.application;

import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClosedPositionFinder {
    private final ClosedPositionRepository closedPositionRepository;

    public List<ClosedPosition> findBetweenDate(PositionOpeningDate openingDate, ClosedPositionClosingDate closingDate){
        return closedPositionRepository.findBetweenDate(openingDate, closingDate);
    }
}
