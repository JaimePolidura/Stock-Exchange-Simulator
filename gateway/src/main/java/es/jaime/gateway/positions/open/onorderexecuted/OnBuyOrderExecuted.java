package es.jaime.gateway.positions.open.onorderexecuted;

import es.jaime.gateway.orders.orders._shared.domain.events.BuyOrderExecuted;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionsRepository;
import es.jaime.gateway.trades._shared.domain.*;
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
        //TODO

//        openPositions.save(new OpenPosition(
//                TradeId.generate(),
//                TradeClientId.of(event.getClientId()),
//                TradeTicker.of(event.getTicker()),
//                TradeOpeningPrice.of(event.getExecutionPrice()),
//                TradeOpeningDate.of(event.getDate()),
//                TradeQuantity.of(event.getQuantity())
//        ));
    }
}
