package es.jaime.gateway.trades.onorderexecuted;

import es.jaime.gateway.orders._shared.domain.events.SellOrderExecuted;
import es.jaime.gateway.trades._shared.domain.TradesRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OnSellOrderExecuted {
    private final TradesRepository repository;

    public OnSellOrderExecuted(TradesRepository tradesRepository) {
        this.repository = tradesRepository;
    }

    @EventListener({SellOrderExecuted.class})
    public void on(SellOrderExecuted event){
        //TODO
    }
}
