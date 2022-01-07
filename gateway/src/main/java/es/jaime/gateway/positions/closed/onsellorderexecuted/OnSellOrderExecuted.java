package es.jaime.gateway.positions.closed.onsellorderexecuted;

import es.jaime.gateway.orders.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.positions._shared.PositionId;
import es.jaime.gateway.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.positions.closed._shared.domain.ClosedPositionRepository;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionFinder;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service("onsellorderexecuted-closedpositions")
public class OnSellOrderExecuted {
    private final ClosedPositionRepository closedPositions;
    private final OpenPositionFinder openPositionFinder;

    public OnSellOrderExecuted(ClosedPositionRepository closedPositions, OpenPositionFinder openPositionFinder) {
        this.closedPositions = closedPositions;
        this.openPositionFinder = openPositionFinder;
    }

    @EventListener({SellOrderExecuted.class})
    @Order(1)
    public void on(SellOrderExecuted event){
        OpenPosition openPosition = openPositionFinder.find(PositionId.of(event.getPositionId()))
                .get();

        closedPositions.save(ClosedPosition.create(
                event.getClientId(),
                event.getTicker(),
                event.getQuantity(),
                openPosition.getOpeningPrice().value(),
                openPosition.getOpeningDate().value(),
                event.getExecutionPrice(),
                event.getDate()
        ));
    }
}
