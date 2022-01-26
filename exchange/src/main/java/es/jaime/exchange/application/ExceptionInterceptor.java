package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.events.EventBus;
import es.jaime.exchange.domain.models.events.ExceptionOccurred;
import es.jaime.exchange.domain.exceptions.DomainException;
import es.jaime.exchange.domain.models.events.OrderMessagePublished;
import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.messages.messages.ErrorOrderMessage;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExceptionInterceptor {
    private final MessagePublisher messagePublisher;
    private final ExchangeConfiguration configuration;
    private final EventBus eventBus;

    @EventListener({ExceptionOccurred.class})
    public void onExceptionOccurred(ExceptionOccurred event){
        DomainException exception = event.getException();
        ExecutionOrder order = exception.getOrderException();

        System.out.println("Exception while executing order: " + order.getOrderId() + " of "
                + order.getClientId() + " " + exception.getMessage());

        messagePublisher.publish(
                configuration.eventsExchangeName(),
                configuration.eventsExchangeName() + ".*." + MessageName.ERROR_ORDER.getName(),
                new ErrorOrderMessage(exception.getMessage(), order.getClientId(), order.getOrderId())
        );

        this.eventBus.publish(new OrderMessagePublished(order.getOrderId()));
    }
}
