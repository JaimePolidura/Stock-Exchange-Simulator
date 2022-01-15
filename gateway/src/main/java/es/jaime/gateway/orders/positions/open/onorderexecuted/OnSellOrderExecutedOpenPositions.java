package es.jaime.gateway.orders.positions.open.onorderexecuted;

import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderExecuted;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


@Service("onsellorderexecuted-trades")
@AllArgsConstructor
public class OnSellOrderExecutedOpenPositions {
    private final OpenPositionsRepository openPositions;

    @EventListener({SellOrderExecuted.class})
    @Order(10)
    public void on(SellOrderExecuted event){
        OpenPosition openPositionToSell = openPositions.findByPositionId(OrderId.of(event.getPositionId()))
                .get();

        if(openPositionToSell.getQuantity().value() > event.getQuantity())
            updateQuantity(openPositionToSell, event);
        else
            deleteOpenPosition(openPositionToSell);
    }

    private void deleteOpenPosition(OpenPosition toDelete) {
        this.openPositions.deleteByPositionId(toDelete.getOrderId());
    }

    private void updateQuantity(OpenPosition openPosition, SellOrderExecuted event) {
        OpenPosition openPositionUpdatedQuantity = openPosition.decreasyQuantityBy(event.getQuantity());

        this.openPositions.save(openPositionUpdatedQuantity);
    }
}
