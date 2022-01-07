package es.jaime.gateway.positions.open.onorderexecuted;

import es.jaime.gateway.orders.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.positions._shared.PositionId;
import es.jaime.gateway.positions._shared.PositionQuantity;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionsRepository;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


@Service("onsellorderexecuted-trades")
public class OnSellOrderExecuted {
    private final OpenPositionsRepository openPositions;

    public OnSellOrderExecuted(OpenPositionsRepository openPositions) {
        this.openPositions = openPositions;
    }

    @EventListener({SellOrderExecuted.class})
    @Order(10)
    public void on(SellOrderExecuted event){
        OpenPosition openPositionToSell = openPositions.findByPositionId(PositionId.of(event.getOpenPositionId()))
                .get();

        if(openPositionToSell.getQuantity().value() > event.getQuantity())
            updateQuantity(openPositionToSell, event);
        else
            deleteOpenPosition(openPositionToSell);
    }

    private void deleteOpenPosition(OpenPosition toDelete) {
        this.openPositions.deleteByPositionId(toDelete.getPositionId());
    }

    private void updateQuantity(OpenPosition openPosition, SellOrderExecuted event) {
        PositionQuantity updatedQuantity = PositionQuantity.of(openPosition.getQuantity().value() - event.getQuantity());

        openPositions.save(new OpenPosition(
                openPosition.getPositionId(),
                openPosition.getClientId(),
                openPosition.getTicker(),
                updatedQuantity,
                openPosition.getOpeningPrice(),
                openPosition.getOpeningDate()
        ));
    }
}
