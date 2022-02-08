package es.jaime.gateway._shared.infrastrocture.exchanges.activeorders;

import es.jaime.gateway._shared.domain.ApplicationConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@AllArgsConstructor
public class RedisConfiguration {
    private final ApplicationConfiguration configuration;

    @Bean
    public Jedis jedis(){
        Jedis jedis = new Jedis(configuration.get("REDIS_HOST"), configuration.getInt("REDIS_PORT"));
        jedis.auth(configuration.get("REDIS_PASSWORD"));

        return jedis;
    }
}
