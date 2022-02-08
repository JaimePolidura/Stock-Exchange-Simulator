package es.jaime.exchange.infrastructure.activeorders;

import es.jaime.exchange.domain.repository.ActiveOrderRepository;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class ActiveOrderRepositoryInRedis implements ActiveOrderRepository {
    private final Jedis jedis;
    private final ExchangeConfiguration configuration;

    public ActiveOrderRepositoryInRedis(Jedis jedis, ExchangeConfiguration configuration) {
        this.jedis = jedis;
        this.configuration = configuration;
    }

    @Override
    public void save(String orderId) {
        jedis.sadd(configuration.exchangeName(), orderId);
    }

    @Override
    public boolean existsByOrderId(String orderId) {
        return jedis.sismember(configuration.exchangeName(), orderId);
    }

    @Override
    public void removeByOrderId(String orderId) {
        jedis.srem(configuration.exchangeName(), orderId);
    }
}
