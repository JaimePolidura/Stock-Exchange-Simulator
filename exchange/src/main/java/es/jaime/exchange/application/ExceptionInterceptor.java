package es.jaime.exchange.application;

import es.jaime.exchange.domain.events.ExceptionOccurredEvent;
import es.jaime.exchange.domain.exceptions.DomainException;
import es.jaime.exchange.domain.models.messages.messages.ErrorOrderMessage;
import es.jaime.exchange.domain.models.messages.EventNames;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MessagePublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ExceptionInterceptor {
    private final MessagePublisher messagePublisher;
    private final ExchangeConfiguration configuration;

    public ExceptionInterceptor(MessagePublisher messagePublisher, ExchangeConfiguration configuration) {
        this.messagePublisher = messagePublisher;
        this.configuration = configuration;
    }

    @EventListener({ExceptionOccurredEvent.class})
    public void onExceptionOccurred(ExceptionOccurredEvent event){
        DomainException exception = event.getException();
        ExecutionOrder order = exception.getOrderException();

        System.out.println("Exception while executing order: " + order.getOrderId() + " of "
                + order.getClientId() + " " + exception.getMessage());

        messagePublisher.publish(
                configuration.eventsExchangeName(),
                configuration.eventsExchangeName() + ".*." + EventNames.ORDER_ERROR,
                new ErrorOrderMessage(exception.getMessage(), order.getClientId(), order.getOrderId())
        );
    }
}
