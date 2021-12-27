package es.jaime.gateway.trades._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.trades._shared.domain.Trade;
import es.jaime.gateway.trades._shared.domain.TradeClientId;
import es.jaime.gateway.trades._shared.domain.TradeId;
import es.jaime.gateway.trades._shared.domain.TradesRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TradesRepositoryMySQL extends HibernateRepository<Trade> implements TradesRepository {
    public TradesRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, Trade.class);
    }

    @Override
    public void save(Trade trade) {
        persist(trade);
    }

    @Override
    public Optional<List<Trade>> findTradesByClientId(TradeClientId clientId) {
        return byQuery("SELECT * FROM trades WHERE trades.clientId = '"+clientId.value()+"'");
    }

    @Override
    public void removeByTradeId(TradeId tradeId) {
        delete("trades", "tradeId = '"+tradeId+"'");
    }
}
