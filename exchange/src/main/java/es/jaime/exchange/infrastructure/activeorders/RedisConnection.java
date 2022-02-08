package es.jaime.exchange.infrastructure.activeorders;

import es.jaime.exchange.domain.services.ExchangeConfiguration;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisConnection {
    private final ExchangeConfiguration configuration;
    private Jedis jedis;

    public RedisConnection(ExchangeConfiguration configuration) {
        this.configuration = configuration;
    }

    public Jedis command(){
        this.connectIfNotConnected();

        return this.jedis;
    }

    private void connectIfNotConnected(){
        if(jedis != null) return;

        this.jedis = new Jedis(configuration.redisHost(), configuration.redisPort());
        this.jedis.auth(configuration.redisPassword());
    }
}
