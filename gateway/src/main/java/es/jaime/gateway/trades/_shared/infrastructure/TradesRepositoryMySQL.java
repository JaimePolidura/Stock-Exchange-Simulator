package es.jaime.gateway.trades._shared.infrastructure;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.trades._shared.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@DependsOn({"hibernate-configuration"})
@Transactional("gateway-transaction-manager")
public class TradesRepositoryMySQL extends HibernateRepository<Trade> implements TradesRepository {
    public TradesRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, Trade.class);
    }

    @Override
    public void save(Trade trade) {
        persist(trade);
    }

    @Override
    public List<Trade> findTradesByClientId(TradeClientId clientId) {
        return byQuery("SELECT * FROM trades WHERE trades.clientId = '"+clientId.value()+"'").get();
    }

    @Override
    public Optional<Trade> findByTradeId(TradeId tradeId) {
        return byId(tradeId);
    }

    @Override
    public void deleteByTradeId(TradeId tradeId) {
        delete("trades", "tradeId = '"+tradeId+"'");
    }

    @Override
    protected Function<Object[], Trade> queryMapper() {
        return primitives -> new Trade(
                TradeId.of(String.valueOf(primitives[0])),
                TradeClientId.of(String.valueOf(primitives[1])),
                TradeTicker.of(String.valueOf(primitives[2])),
                TradeOpeningPrice.of(Double.parseDouble(String.valueOf(primitives[3]))),
                TradeOpeningDate.of(String.valueOf(primitives[4])),
                TradeQuantity.of(Integer.parseInt(String.valueOf(primitives[5])))
        );
    }
}
