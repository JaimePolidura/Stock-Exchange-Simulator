package es.jaime.exchange.infrastructure.activeorders;

import es.jaime.exchange.domain.repository.ActiveOrderRepository;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveOrderRepositoryInRedis implements ActiveOrderRepository {
    private final RedisConnection redis;
    private final ExchangeConfiguration configuration;

    public ActiveOrderRepositoryInRedis(RedisConnection redis, ExchangeConfiguration configuration) {
        this.redis = redis;
        this.configuration = configuration;
    }

    @Override
    public void save(String orderId) {
        this.redis.command().sadd(configuration.exchangeName(), orderId);
    }

    @Override
    public boolean existsByOrderId(String orderId) {
        return redis.command().sismember(configuration.exchangeName(), orderId);
    }

    @Override
    public void removeByOrderId(String orderId) {
        redis.command().srem(configuration.exchangeName(), orderId);
    }
}
