package es.jaime.gateway.orders.positions.open.onorderexecuted;

import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderExecuted;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnBuyOrderExecutedPositions {
    private final OpenPositionsRepository openPositions;

    public OnBuyOrderExecutedPositions(OpenPositionsRepository repository) {
        this.openPositions = repository;
    }

    @EventListener({BuyOrderExecuted.class})
    public void on(BuyOrderExecuted event){
        openPositions.save(OpenPosition.create(
                event.getClientId(),
                event.getTicker(),
                event.getExecutionPrice(),
                event.getQuantity()
        ));
    }
}
