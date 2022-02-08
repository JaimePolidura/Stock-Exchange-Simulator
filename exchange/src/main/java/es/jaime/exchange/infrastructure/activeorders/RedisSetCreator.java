package es.jaime.exchange.infrastructure.activeorders;

import es.jaime.exchange.domain.services.ExchangeConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
@AllArgsConstructor
public class RedisSetCreator implements CommandLineRunner {
    private final ActiveOrderRepositoryInRedis redis;
    private final ExchangeConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {
        redis.save("start");
    }
}
