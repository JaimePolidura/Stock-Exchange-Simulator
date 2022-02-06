package es.jaime.gateway.orders.positions.open.onorderexecuted;

import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderExecuted;
import es.jaime.gateway.orders.positions._shared.domain.ExecutedOrderType;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningDate;
import es.jaime.gateway.orders.positions._shared.domain.PositionOpeningPrice;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OnBuyOrderOpenPositions {
    private final OpenPositionsRepository openPositions;

    @EventListener({BuyOrderExecuted.class})
    public void on(BuyOrderExecuted event){
        OrderDate dateNow = OrderDate.now();
        
        openPositions.save(new OpenPosition(
                OrderId.of(event.getNewPositionId()),
                OrderClientId.of(event.getClientId()),
                dateNow,
                OrderState.executed(),
                OrderTicker.of(event.getTicker()),
                OrderQuantity.of(event.getQuantity()),
                ExecutedOrderType.open(),
                PositionOpeningPrice.of(event.getPriceToExecute()),
                PositionOpeningDate.of(dateNow.value())
        ));
    }
}
