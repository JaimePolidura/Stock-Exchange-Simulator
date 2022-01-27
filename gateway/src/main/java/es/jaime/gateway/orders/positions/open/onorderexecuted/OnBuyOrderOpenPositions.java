package es.jaime.gateway.orders.positions.open.onorderexecuted;

import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderExecuted;
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
        openPositions.save(OpenPosition.create(
                event.getClientId(),
                event.getTicker(),
                event.getPriceToExecute(),
                event.getQuantity()
        ));
    }
}
