package es.jaime.gateway.orders.positions.closed.onsellorderexecuted;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderExecuted;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionRepository;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionFinder;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service("onsellorderexecuted-closedpositions")
@AllArgsConstructor
public class OnSellOrderExecutedClosedPositions {
    private final ClosedPositionRepository closedPositions;
    private final OpenPositionFinder openPositionFinder;

    @EventListener({SellOrderExecuted.class})
    @Order(1)
    public void on(SellOrderExecuted event){
        OpenPosition openPosition = openPositionFinder.find(OrderId.of(event.getPositionId()))
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
