package es.jaime.gateway.orders.pendingorder.cancel.send;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.OrderDate;
import es.jaime.gateway.orders._shared.domain.OrderId;
import es.jaime.gateway.orders._shared.domain.OrderState;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderIdToCancel;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.NEW_ORDERS_EXCHNAGE;
import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.newOrdersQueueName;

@Service
public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand> {
    private final ExecutionOrderFinder executionOrderFinder;
    private final OrdersCancelRepository ordersCancel;

    private final MessagePublisher messagePublisher;

    public CancelOrderCommandHandler(ExecutionOrderFinder executionOrderFinder, OrdersCancelRepository ordersCancel,
                                     MessagePublisher messagePublisher) {
        this.executionOrderFinder = executionOrderFinder;
        this.ordersCancel = ordersCancel;
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
        OrderTicker tickerOfOrderToCancel = getTickerFromOrderId(command.getOrderIdToCancel());

        this.ordersCancel.save(new CancelOrder(
                command.getOrderId(),
                command.getClientId(),
                OrderDate.now(),
                OrderState.pending(),
                tickerOfOrderToCancel,
                PendingOrderType.cancel(),
                OrderIdToCancel.of(command.getOrderIdToCancel().value())
        ));
    }

    private void publishMessage(CancelOrderCommand command) {
        OrderTicker ticker = getTickerFromOrderId(command.getOrderIdToCancel());

        this.messagePublisher.publish(
                NEW_ORDERS_EXCHNAGE,
                newOrdersQueueName(ListedCompanyTicker.of(ticker.value())),
                CancelOrderMessage.create(
                        command.getOrderId(),
                        command.getClientId(),
                        command.getOrderIdToCancel(),
                        ticker
                )
        );
    }

    private OrderTicker getTickerFromOrderId(OrderId orderIdToCancel){
        return executionOrderFinder.findById(orderIdToCancel)
                .get()
                .getTicker();
    }

    private void ensureOrderExists(CancelOrderCommand command){
        executionOrderFinder.findById(command.getOrderIdToCancel())
                .orElseThrow(() -> new ResourceNotFound("Order not found"));
    }

    private void ensureOrderOwned(CancelOrderCommand command){
        boolean owned = executionOrderFinder.findById(command.getOrderIdToCancel())
                .get()
                .getClientId().equals(command.getClientId());

        if(!owned){
            throw new NotTheOwner("You dont own that order");
        }
    }
}
