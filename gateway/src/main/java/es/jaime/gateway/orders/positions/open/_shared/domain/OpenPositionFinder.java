package es.jaime.gateway.orders.positions.open._shared.domain;

import es.jaime.gateway.orders._shared.domain.OrderId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OpenPositionFinder {
    private final OpenPositionsRepository openPositions;

    public Optional<OpenPosition> find(OrderId id){
        return openPositions.findByPositionId(id);
    }
}
