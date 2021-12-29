package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.authentication._shared.domain.UserName;
import org.springframework.stereotype.Service;

@Service
public class TradeFinderService {
    private final TradesRepository trades;

    public TradeFinderService(TradesRepository trades) {
        this.trades = trades;
    }

    public Trade find(TradeId tradeId){
        return trades.findByTradeId(tradeId)
                .orElseThrow(() -> new ResourceNotFound("Trade not found"));
    }
}
