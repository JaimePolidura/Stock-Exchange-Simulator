package es.jaime.gateway._shared.infrastrocture.exchanges.ordresrestorer;

import es.jaime.gateway._shared.domain.messages.Message;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrder;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderFinder;
import lombok.AllArgsConstructor;

import java.util.List;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@AllArgsConstructor
public class ExchangeOrderRestorerService implements Runnable{
    private final PendingOrderFinder pendingOrderFinder;
    private final MessagePublisher messagePublisher;
    private final ListedCompany listedCompany;

    @Override
    public void run() {
        List<PendingOrder> pendingOrders = pendingOrderFinder.findPendingOrdersFor(listedCompany);

        for (PendingOrder pendingOrder : pendingOrders) {
            Message message = pendingOrder.toMessage();

            messagePublisher.publish(NEW_ORDERS_EXCHNAGE, newOrdersQueueName(listedCompany.ticker()), message);
        }
    }
}
