package es.jaime.gateway.trades.onorderexecuted;

import es.jaime.gateway.orders._shared.domain.events.BuyOrderExecuted;
import es.jaime.gateway.trades._shared.domain.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnBuyOrderExecuted {
    private final TradesRepository repository;

    public OnBuyOrderExecuted(TradesRepository repository) {
        this.repository = repository;
    }

    @EventListener({BuyOrderExecuted.class})
    public void on(BuyOrderExecuted event){
        repository.save(new Trade(
                TradeId.generate(),
                TradeClientId.of(event.getClientId()),
                TradeTicker.of(event.getTicker()),
                TradeOpeningPrice.of(event.getExecutionPrice()),
                TradeOpeningDate.of(event.getDate()),
                TradeQuantity.of(event.getQuantity())
        ));
    }
}
