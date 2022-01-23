package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.events.EventBus;
import es.jaime.exchange.domain.models.events.OrderMessagePublished;
import es.jaime.exchange.domain.models.messages.MessageNames;
import es.jaime.exchange.domain.models.messages.messages.OrderCancelledMessage;
import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MessagePublisher;
import es.jaime.exchange.domain.services.OrderCancellationProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderCancellationProcessorImpl implements OrderCancellationProcessor {
    private final MessagePublisher messagePublisher;
    private final ExchangeConfiguration configuration;
    private final EventBus eventBus;

    @Override
    public void cancel(ExecutionOrder order, CancelOrder cancelOrder) {
        this.messagePublisher.publish(
                configuration.eventsExchangeName(),
                configuration.eventsExchangeName() + ".*." + MessageNames.ORDER_CANCELLED.getName(),
                new OrderCancelledMessage(order.getClientId(), cancelOrder.getOrderId(), order.getOrderId())
        );

        this.eventBus.publish(new OrderMessagePublished(order.getOrderId(), cancelOrder.getOrderId()));
    }
}
