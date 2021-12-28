package es.jaime.gateway.trades.onorderexecuted;

import es.jaime.gateway.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.trades._shared.domain.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OnSellOrderExecuted {
    private final TradesRepository repository;

    public OnSellOrderExecuted(TradesRepository tradesRepository) {
        this.repository = tradesRepository;
    }

    @EventListener({SellOrderExecuted.class})
    public void on(SellOrderExecuted event){
        Optional<Trade> tradeToSellOptional = repository.findByTradeId(TradeId.of(event.getTradeId()));

        if(tradeToSellOptional.isEmpty()) return;

        Trade tradeToSell = tradeToSellOptional.get();

        if(tradeToSell.quantity().value() > event.getQuantity()){
            updateQuantityToTradeToSell(tradeToSell, event);
        }else{
            deleteTradeToSell(tradeToSell.tradeId());
        }
    }

    private void deleteTradeToSell(TradeId tradeId) {
        this.repository.deleteByTradeId(tradeId);
    }

    private void updateQuantityToTradeToSell(Trade tradeToSell, SellOrderExecuted event) {
        TradeQuantity updatedQuantity = TradeQuantity.of(tradeToSell.quantity().value() - event.getQuantity());

        repository.save(new Trade(
                tradeToSell.tradeId(),
                tradeToSell.clientId(),
                tradeToSell.ticker(),
                tradeToSell.openingPrice(),
                tradeToSell.openingDate(),
                updatedQuantity
        ));
    }
}
