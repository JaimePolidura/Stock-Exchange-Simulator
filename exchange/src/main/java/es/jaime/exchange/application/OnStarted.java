package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.messages.messages.ExchangeStarted;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Order(999999) //Last commandlineruner to be executed
public class OnStarted implements CommandLineRunner {
    private final MessagePublisher messagePublisher;
    private final ExchangeConfiguration configuration;

    @Override
    public void run(String... args) throws Exception {
        messagePublisher.publish(
                configuration.eventsExchangeName(),
                String.format(configuration.eventsRoutingKey(), MessageName.EXCHANGE_STARTED.getName()),
                new ExchangeStarted(
                        configuration.ticker(),
                        configuration.exchangeName()
                )
        );
    }
}
