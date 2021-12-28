package es.jaime.gateway.trades._shared.domain;

import java.util.List;
import java.util.Optional;

public interface TradesRepository {
    void save(Trade trade);

    List<Trade> findTradesByClientId(TradeClientId clientId);

    Optional<Trade> findByTradeId(TradeId tradeId);

    void deleteByTradeId(TradeId tradeId);
}
