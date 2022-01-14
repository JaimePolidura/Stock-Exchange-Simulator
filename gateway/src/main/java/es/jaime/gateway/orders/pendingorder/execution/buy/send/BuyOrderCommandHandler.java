package es.jaime.gateway.orders.pendingorder.execution.buy.send;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderQuantity;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.NEW_ORDERS_EXCHNAGE;
import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.newOrdersQueueName;

@Service
public class BuyOrderCommandHandler implements CommandHandler<BuyOrderCommand> {
    private final MessagePublisher queuePublisher;
    private final BuyOrderRepostory buyOrders;
    private final ListedCompanyFinderService listedCompanyFinder;

    public BuyOrderCommandHandler(MessagePublisher queuePublisher, BuyOrderRepostory buyOrders,
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
        this.buyOrders.save(new BuyOrder(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderState.pending(),
                command.getTicker(),
                PendingOrderType.buy(),
                command.getQuantity(),
                command.getExecutionPrice()
        ));

        if(!hasBeenSaved(command.getOrderID())){
            saveOrderToRepository(command);
        }
    }

    private boolean hasBeenSaved(OrderId orderId){
        return buyOrders.findByOrderId(orderId).isPresent();
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
                PendingOrderType.buy()
        ));
    }
}
