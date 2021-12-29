package es.jaime.gateway.trades.onorderexecuted;

import es.jaime.gateway.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.trades._shared.domain.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnSellOrderExecuted {
    private final TradesRepository trades;

    public OnSellOrderExecuted(TradesRepository tradesRepository) {
        this.trades = tradesRepository;
    }

    @EventListener({SellOrderExecuted.class})
    public void on(SellOrderExecuted event){
        Optional<Trade> tradeToSellOptional = trades.findByTradeId(TradeId.of(event.getTradeId()));

        if(tradeToSellOptional.isEmpty()) return;

        Trade tradeToSell = tradeToSellOptional.get();

        if(tradeToSell.getQuantity().value() > event.getQuantity())
            updateQuantityToTradeToSell(tradeToSell, event);
        else
            deleteTradeToSell(tradeToSell.getTradeId());
    }

    private void deleteTradeToSell(TradeId tradeId) {
        this.trades.deleteByTradeId(tradeId);
    }

    private void updateQuantityToTradeToSell(Trade tradeToSell, SellOrderExecuted event) {
        TradeQuantity updatedQuantity = TradeQuantity.of(tradeToSell.getQuantity().value() - event.getQuantity());

        trades.save(new Trade(
                tradeToSell.getTradeId(),
                tradeToSell.getClientId(),
                tradeToSell.getTicker(),
                tradeToSell.getOpeningPrice(),
                tradeToSell.getOpeningDate(),
                updatedQuantity
        ));
    }
}
