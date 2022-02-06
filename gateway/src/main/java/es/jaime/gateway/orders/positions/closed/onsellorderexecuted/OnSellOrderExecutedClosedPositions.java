package es.jaime.gateway.orders.positions.closed.onsellorderexecuted;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderExecuted;
import es.jaime.gateway.orders.positions._shared.domain.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPosition;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingDate;
import es.jaime.gateway.orders.positions.closed._shared.domain.ClosedPositionClosingPrice;
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

        closedPositions.save(new ClosedPosition(
                OrderId.of(event.getNewPositionId()),
                OrderClientId.of(event.getClientId()),
                OrderDate.now(),
                OrderState.executed(),
                OrderTicker.of(event.getTicker()),
                OrderQuantity.of(event.getQuantity()),
                ExecutedOrderType.closed(),
                PositionOpeningPrice.of(openPosition.getOpeningPrice().value()),
                PositionOpeningDate.of(openPosition.getOpeningDate().value()),
                ClosedPositionClosingPrice.of(event.getPriceToExecute()),
                ClosedPositionClosingDate.of(event.getDate())
        ));
    }
}
