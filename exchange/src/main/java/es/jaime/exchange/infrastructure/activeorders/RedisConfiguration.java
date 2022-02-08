package es.jaime.exchange.infrastructure.activeorders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfiguration {
    @Bean
    public Jedis jedis(){
        Jedis jedis = new Jedis("active-orders-redis", 6379);
        jedis.auth("29oumr982oyvtpiou2");

        return jedis;
    }
}
