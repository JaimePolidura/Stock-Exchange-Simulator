package es.jaime.gateway.orders.pendingorder.execution.buy.send;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.EVENTS_EXCHANGE;
import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.newOrdersQueueName;

@Service
@AllArgsConstructor
public class BuyOrderCommandHandler implements CommandHandler<BuyOrderCommand> {
    private final MessagePublisher queuePublisher;
    private final BuyOrderRepostory buyOrders;
    private final ListedCompanyFinderService listedCompanyFinder;

    @Override
    public void handle(BuyOrderCommand command) {
        ensureTickerExists(command);

        saveOrderToRepository(command);
        publishMessage(command);
    }

    private void ensureTickerExists(BuyOrderCommand command){
        //If it is not found the service will throw ResourceNotFound exception
        listedCompanyFinder.find(ListedCompanyTicker.of(command.getTicker().value()));
    }

    private void saveOrderToRepository(BuyOrderCommand command){
        this.buyOrders.save(new BuyOrder(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderState.pending(),
                command.getTicker(),
                PendingOrderType.buy(),
                command.getQuantity(),
                command.getPriceToExecute()
        ));
    }

    private void publishMessage(BuyOrderCommand command){
        String routingKey = newOrdersQueueName(ListedCompanyTicker.of(command.getTicker().value()));

        this.queuePublisher.publish(
                EVENTS_EXCHANGE,
                routingKey,
                new SendBuyOrderMessage(
                        command.getOrderID(),
                        command.getClientID(),
                        command.getOrderDate(),
                        command.getPriceToExecute(),
                        command.getQuantity(),
                        command.getTicker(),
                        PendingOrderType.buy()
        ));
    }
}
