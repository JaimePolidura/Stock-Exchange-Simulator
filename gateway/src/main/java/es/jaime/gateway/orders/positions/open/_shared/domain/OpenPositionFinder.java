package es.jaime.gateway.orders.positions.open._shared.domain;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OpenPositionFinder {
    private final OpenPositionsRepository openPositions;

    public OpenPositionFinder(OpenPositionsRepository openPositions) {
        this.openPositions = openPositions;
    }

    public Optional<OpenPosition> find(PositionId id){
        return openPositions.findByPositionId(id);
    }
}
