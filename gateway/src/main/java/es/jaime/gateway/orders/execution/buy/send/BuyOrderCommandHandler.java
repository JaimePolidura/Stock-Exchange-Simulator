package es.jaime.gateway.orders.execution.buy.send;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.OrderType;
import es.jaime.gateway.orders.execution.buy._shared.domain.OrderBuy;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.execution.buy._shared.domain.BuyOrderRepostiry;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.NEW_ORDERS_EXCHNAGE;
import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.newOrdersQueueName;

@Service
public class BuyOrderCommandHandler implements CommandHandler<BuyOrderCommand> {
    private final MessagePublisher queuePublisher;
    private final BuyOrderRepostiry buyOrders;
    private final ListedCompanyFinderService listedCompanyFinder;

    public BuyOrderCommandHandler(MessagePublisher queuePublisher, BuyOrderRepostiry buyOrders,
                                   ListedCompanyFinderService listedCompanyFinder) {
        this.queuePublisher = queuePublisher;
        this.buyOrders = buyOrders;
        this.listedCompanyFinder = listedCompanyFinder;
    }

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
        this.buyOrders.save(new OrderBuy(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderType.buy(),
                OrderState.pending(),
                command.getTicker(),
                command.getQuantity(),
                command.getExecutionPrice()
        ));
    }

    private void publishMessage(BuyOrderCommand command){
        String routingKey = newOrdersQueueName(ListedCompanyTicker.of(command.getTicker().value()));

        this.queuePublisher.publish(NEW_ORDERS_EXCHNAGE, routingKey, new SendBuyOrderMessage(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                command.getExecutionPrice(),
                command.getQuantity(),
                command.getTicker(),
                OrderTypeId.buy()
        ));
    }
}
