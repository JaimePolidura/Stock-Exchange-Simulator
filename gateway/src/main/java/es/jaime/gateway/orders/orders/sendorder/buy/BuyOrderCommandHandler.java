package es.jaime.gateway.orders.orders.sendorder.buy;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.messagePublisher.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.orders._shared.domain.orderbody.OrderBodyBuy;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Service
public class BuyOrderCommandHandler implements CommandHandler<BuyOrderCommand> {
    private final MessagePublisher queuePublisher;
    private final OrdersRepository repository;
    private final ListedCompanyFinderService listedCompanyFinder;

    public BuyOrderCommandHandler(MessagePublisher queuePublisher, OrdersRepository repository,
                                   ListedCompanyFinderService listedCompanyFinder) {
        this.queuePublisher = queuePublisher;
        this.repository = repository;
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
        listedCompanyFinder.find(ListedCompanyTicker.of(command.getTicker()));
    }

    private void saveOrderToRepository(BuyOrderCommand command){
        this.repository.save(new Order(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderTypeId.buy(),
                OrderBodyBuy.of(
                        command.getTicker(),
                        command.getQuantity(),
                        command.getExecutionPrice()
                )
        ));
    }

    private void publishMessage(BuyOrderCommand command){
        String routingKey = newOrdersQueueName(ListedCompanyTicker.of(command.getTicker()));

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
