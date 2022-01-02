package es.jaime.gateway.orders.sendorder.buy;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.messagePublisher.MessagePublisher;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQDeclarables;
import es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrderType;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;
import static es.jaime.gateway.orders._shared.domain.OrderTypeValues.*;

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

        this.repository.save(new Order(
                command.getOrderID(),
                command.getTicker(),
                command.getClientID(),
                command.getOrderDate(),
                command.getQuantity(),
                OrderType.of(BUY),
                command.getExecutionPrice()
        ));

        publishMessage(command);
    }

    private void ensureTickerExists(BuyOrderCommand command){
        //If it is not found the service will throw ResourceNotFound exception
        listedCompanyFinder.find(ListedCompanyTicker.of(command.getTicker().value()));
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
                OrderType.of(BUY)
        ));
    }
}
