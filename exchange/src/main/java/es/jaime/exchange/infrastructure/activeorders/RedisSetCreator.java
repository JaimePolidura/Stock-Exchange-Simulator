package es.jaime.exchange.infrastructure.activeorders;

import es.jaime.exchange.domain.services.ExchangeConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
@Order(10)
@AllArgsConstructor
public class RedisSetCreator implements CommandLineRunner {
    private final Jedis jedis;
    private final ExchangeConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("lkajslka");
        System.out.println(configuration.exchangeName());

        jedis.sadd(configuration.exchangeName(), "start");
    }
}
