package es.jaime.gateway._shared.infrastrocture.exchanges.ordresrestorer;

import es.jaime.gateway._shared.domain.messages.Message;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
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
    private final MessagePublisher messagePublisher;

    @EventListener({ExchangeStarted.class})
    public void on(ExchangeStarted event){
        List<PendingOrder> pendingOrders = pendingOrderFinder.findPendingOrdersFor(OrderTicker.of(event.getTicker()));

        for (PendingOrder pendingOrder : pendingOrders) {
            Message message = pendingOrder.toMessage();

            messagePublisher.publish(NEW_ORDERS_EXCHNAGE, newOrdersQueueName(ListedCompanyTicker.of(event.getTicker())), message);
        }
    }
}
