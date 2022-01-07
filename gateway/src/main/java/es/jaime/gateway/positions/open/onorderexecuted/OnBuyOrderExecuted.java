package es.jaime.gateway.positions.open.onorderexecuted;

import es.jaime.gateway.orders.orders._shared.domain.events.BuyOrderExecuted;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionsRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnBuyOrderExecuted {
    private final OpenPositionsRepository openPositions;

    public OnBuyOrderExecuted(OpenPositionsRepository repository) {
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
