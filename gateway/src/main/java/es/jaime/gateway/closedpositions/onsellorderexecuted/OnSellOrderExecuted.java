package es.jaime.gateway.closedpositions.onsellorderexecuted;

import es.jaime.gateway.closedpositions._shared.domain.ClosedPosition;
import es.jaime.gateway.closedpositions._shared.domain.ClosedPositionRepository;
import es.jaime.gateway.orders.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.trades._shared.domain.Trade;
import es.jaime.gateway.trades._shared.domain.TradeFinderService;
import es.jaime.gateway.trades._shared.domain.TradeId;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service("onsellorderexecuted-closedpositions")
public class OnSellOrderExecuted {
    private final ClosedPositionRepository closedPositions;
    private final TradeFinderService tradeFinderService;

    public OnSellOrderExecuted(ClosedPositionRepository closedPositions, TradeFinderService tradeFinderService) {
        this.closedPositions = closedPositions;
        this.tradeFinderService = tradeFinderService;
    }

    @EventListener({SellOrderExecuted.class})
    @Order(1)
    public void on(SellOrderExecuted event){
        Trade trade = tradeFinderService.find(TradeId.of(event.getTradeId()));

        closedPositions.save(ClosedPosition.create(
                event.getClientId(),
                event.getTicker(),
                event.getQuantity(),
                trade.getOpeningPrice().value(),
                trade.getOpeningDate().value(),
                event.getExecutionPrice(),
                event.getDate()
        ));
    }
}
