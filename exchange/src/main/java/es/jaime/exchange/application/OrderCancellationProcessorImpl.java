package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.messages.EventNames;
import es.jaime.exchange.domain.models.messages.OrderCancelledMessage;
import es.jaime.exchange.domain.models.orders.CancelOrder;
import es.jaime.exchange.domain.models.orders.Order;
import es.jaime.exchange.domain.services.ExchangeConfiguration;
import es.jaime.exchange.domain.services.MessagePublisher;
import es.jaime.exchange.domain.services.OrderCancellationProcessor;
import org.springframework.stereotype.Service;

@Service
public class OrderCancellationProcessorImpl implements OrderCancellationProcessor {
    private final MessagePublisher messagePublisher;
    private final ExchangeConfiguration configuration;

    public OrderCancellationProcessorImpl(MessagePublisher messagePublisher, ExchangeConfiguration configuration) {
        this.messagePublisher = messagePublisher;
        this.configuration = configuration;
    }

    @Override
    public void cancel(Order order, CancelOrder cancelOrder) {
        this.messagePublisher.publish(
                configuration.eventsExchangeName(),
                configuration.eventsExchangeName() + ".*." + EventNames.ORDER_CANCELLED.getName(),
                new OrderCancelledMessage(order.getClientId(), cancelOrder.getOrderId(), order.getOrderId())
        );
    }
}
