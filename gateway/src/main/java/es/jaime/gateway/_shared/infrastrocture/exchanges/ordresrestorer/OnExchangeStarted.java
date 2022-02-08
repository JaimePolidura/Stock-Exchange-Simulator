package es.jaime.gateway._shared.infrastrocture.exchanges.ordresrestorer;

import es.jaime.gateway._shared.domain.exchange.activeorders.ActiveOrdersRepository;
import es.jaime.gateway._shared.domain.messages.Message;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrder;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderFinder;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@AllArgsConstructor
@Service
public class OnExchangeStarted{
    private final PendingOrderFinder pendingOrderFinder;
    private final ActiveOrdersRepository activeOrders;
    private final MessagePublisher messagePublisher;

    @EventListener({ExchangeStarted.class})
    public void on(ExchangeStarted event){
        List<OrderId> ordersId = activeOrders.findOrdersByExchangeName(event.getExchangeName());
        List<PendingOrder> pendingOrders = pendingOrderFinder.findPendingOrdersByOrderId(ordersId);

        for (PendingOrder pendingOrder : pendingOrders) {
            Message message = pendingOrder.toMessage();

            messagePublisher.publish(EVENTS_EXCHANGE, newOrdersQueueName(event.getTicker()), message);
        }
    }
}
