package es.jaime.gateway.trades._shared.domain;

import java.util.List;
import java.util.Optional;

public interface TradesRepository {
    void save(Trade trade);

    Optional<List<Trade>> findTradesByClientId(TradeClientId clientId);

    void removeByTradeId(TradeId tradeId);
}
