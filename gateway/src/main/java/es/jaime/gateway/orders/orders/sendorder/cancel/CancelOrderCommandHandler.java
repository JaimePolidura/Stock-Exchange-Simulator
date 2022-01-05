package es.jaime.gateway.orders.orders.sendorder.cancel;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders.orders._shared.application.OrderFinderService;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.orders._shared.domain.orderbody.OrderBodyCancel;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Service
public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand> {
    private final OrderFinderService orderFinder;
    private final OrdersRepository orders;
    private final MessagePublisher messagePublisher;

    public CancelOrderCommandHandler(OrderFinderService orderFinder, OrdersRepository orders,
                                     MessagePublisher messagePublisher) {
        this.orderFinder = orderFinder;
        this.orders = orders;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void handle(CancelOrderCommand command) {
        ensureOrderExists(command);
        ensureOrderOwned(command);

        saveOrderToRepostiory(command);
        publishMessage(command);
    }

    private void saveOrderToRepostiory(CancelOrderCommand command) {
        this.orders.save(new Order(
                command.getOrderId(),
                command.getClientId(),
                OrderDate.now(),
                OrderTypeId.cancel(),
                OrderBodyCancel.of(command.getOrderIdToCancel())
        ));
    }

    private void publishMessage(CancelOrderCommand command) {
        String ticker = getTickerFromOrderId(command.getOrderIdToCancel());

        this.messagePublisher.publish(
                NEW_ORDERS_EXCHNAGE,
                newOrdersQueueName(ListedCompanyTicker.of(ticker)),
                CancelOrderMessage.create(
                        command.getOrderId(),
                        command.getClientId(),
                        command.getOrderIdToCancel(),
                        ticker
                )
        );
    }

    private String getTickerFromOrderId(OrderId orderIdToCancel){
        return orderFinder.findByOrderId(orderIdToCancel)
                .get()
                .getBody()
                .getString("ticker");
    }

    private void ensureOrderExists(CancelOrderCommand command){
        orderFinder.findByOrderId(command.getOrderIdToCancel())
                .orElseThrow(() -> new ResourceNotFound("Order not found"));
    }

    private void ensureOrderOwned(CancelOrderCommand command){
        boolean owned = orderFinder.findByOrderId(command.getOrderIdToCancel())
                .get()
                .getClientId().equals(command.getClientId());

        if(!owned){
            throw new NotTheOwner("You dont own that order");
        }
    }
}
