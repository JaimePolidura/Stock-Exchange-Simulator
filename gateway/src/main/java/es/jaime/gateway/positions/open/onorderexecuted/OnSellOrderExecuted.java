package es.jaime.gateway.positions.open.onorderexecuted;

import es.jaime.gateway.orders.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionsRepository;
import es.jaime.gateway.trades._shared.domain.Trade;
import es.jaime.gateway.trades._shared.domain.TradeId;
import es.jaime.gateway.trades._shared.domain.TradeQuantity;
import es.jaime.gateway.trades._shared.domain.TradesRepository;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("onsellorderexecuted-trades")
public class OnSellOrderExecuted {
    private final OpenPositionsRepository trades;

    public OnSellOrderExecuted(OpenPositionsRepository tradesRepository) {
        this.trades = tradesRepository;
    }

    @EventListener({SellOrderExecuted.class})
    @Order(10)
    public void on(SellOrderExecuted event){
        //TODO

//        Optional<Trade> tradeToSellOptional = trades.findByTradeId(TradeId.of(event.getTradeId()));
//
//        if(tradeToSellOptional.isEmpty()) return;
//
//        Trade tradeToSell = tradeToSellOptional.get();
//
//        if(tradeToSell.getQuantity().value() > event.getQuantity())
//            updateQuantityToTradeToSell(tradeToSell, event);
//        else
//            deleteTradeToSell(tradeToSell.getPositionId());
    }

    private void deleteTradeToSell(TradeId tradeId) {
//        this.trades.deleteByTradeId(tradeId);
    }

//    private void updateQuantityToTradeToSell(Trade tradeToSell, SellOrderExecuted event) {
//        TradeQuantity updatedQuantity = TradeQuantity.of(tradeToSell.getQuantity().value() - event.getQuantity());
//
//        trades.save(new Trade(
//                tradeToSell.getPositionId(),
//                tradeToSell.getClientId(),
//                tradeToSell.getTicker(),
//                tradeToSell.getOpeningPrice(),
//                tradeToSell.getOpeningDate(),
//                updatedQuantity
//        ));
//    }
}
