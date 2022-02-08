package es.jaime.gateway._shared.infrastrocture.exchanges.activeorders;

import es.jaime.gateway._shared.domain.exchange.activeorders.ActiveOrdersRepository;
import es.jaime.gateway.orders._shared.domain.OrderId;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActiveOrdersRepositoryInRedis implements ActiveOrdersRepository {
    private final Jedis jedis;

    public ActiveOrdersRepositoryInRedis(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public List<OrderId> findOrdersByExchangeName(String exchangeName) {
        return this.jedis.smembers(exchangeName).stream()
                .map(OrderId::of)
                .collect(Collectors.toList());
    }
}
