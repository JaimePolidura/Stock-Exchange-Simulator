package es.jaime.gateway.orders.pendingorder.execution.sell.send;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.*;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.OrderPositionIdToSell;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrder;
import es.jaime.gateway.orders.pendingorder.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.orders.positions.open._shared.domain.OpenPositionFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Service
@AllArgsConstructor
public class SellOrderCommandHandler implements CommandHandler<SellOrderCommand> {
    private final SellOrderRepostiry sellOrders;
    private final MessagePublisher messagePublisher;
    private final OpenPositionFinder openPositionFinder;

    @Override
    public void handle(SellOrderCommand command) {
        OpenPosition openPositionToSell = openPositionFinder.find(command.getPositionId())
                .orElseThrow(() -> new ResourceNotFound("Order id not found"));

        ensureOwnsOpenPosition(openPositionToSell, command.getClientID());
        ensureCorrectQuantity(openPositionToSell, command.getQuantity());

        saveToRepository(command, openPositionToSell);
        publishMessage(command, openPositionToSell);
    }

    private void ensureOwnsOpenPosition(OpenPosition positionToSell, OrderClientId clientID){
        if(!positionToSell.getClientId().value().equalsIgnoreCase(clientID.value())){
            throw new NotTheOwner("You are not the owner of that open position");
        }
    }

    private void ensureCorrectQuantity(OpenPosition positionToSell, OrderQuantity quantity){
        if(quantity.value() > positionToSell.getQuantity().value()){
            throw new IllegalQuantity("You cant sell more than you have");
        }
    }

    private void saveToRepository(SellOrderCommand command, OpenPosition positionToSell){
        this.sellOrders.save(new SellOrder(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderState.pending(),
                OrderTicker.of(positionToSell.getTicker().value()),
                PendingOrderType.sell(),
                command.getQuantity(),
                command.getExecutionPrice(),
                OrderPositionIdToSell.of(positionToSell.getOrderId().value())
        ));

        if(!hasBeenSaved(command.getOrderID())){
            saveToRepository(command, positionToSell);
        }
    }

    private boolean hasBeenSaved(OrderId orderId){
        return sellOrders.findByOrderId(orderId).isPresent();
    }

    private void publishMessage(SellOrderCommand command, OpenPosition positionToSell){
        String routingKey = newOrdersQueueName(ListedCompanyTicker.of(positionToSell.getTicker().value()));

        messagePublisher.publish(NEW_ORDERS_EXCHNAGE, routingKey, new SendSellOrderMessage(
                command.getOrderID(),
                OrderPositionIdToSell.of(positionToSell.getOrderId().value()),
                command.getClientID(),
                command.getOrderDate(),
                command.getExecutionPrice(),
                command.getQuantity(),
                positionToSell.getTicker(),
                PendingOrderType.sell())
        );
    }
}
