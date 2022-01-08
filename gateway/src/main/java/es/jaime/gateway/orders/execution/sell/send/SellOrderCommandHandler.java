package es.jaime.gateway.orders.execution.sell.send;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.execution.sell._shared.domain.OrderSell;
import es.jaime.gateway.orders.execution.sell._shared.domain.SellOrderRepostiry;
import es.jaime.gateway.orders.newmodel._shared.domain.*;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionFinder;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Service
public class SellOrderCommandHandler implements CommandHandler<SellOrderCommand> {
    private final SellOrderRepostiry sellOrders;
    private final MessagePublisher messagePublisher;
    private final OpenPositionFinder openPositionFinder;

    public SellOrderCommandHandler(SellOrderRepostiry sellOrders, MessagePublisher messagePublisher,
                                   OpenPositionFinder openPositionFinder) {
        this.sellOrders = sellOrders;
        this.messagePublisher = messagePublisher;
        this.openPositionFinder = openPositionFinder;
    }

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
        this.sellOrders.save(new OrderSell(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderType.sell(),
                OrderState.pending(),
                OrderTicker.of(positionToSell.getTicker().value()),
                command.getQuantity(),
                command.getExecutionPrice(),
                OrderPositionIdToSell.of(positionToSell.getPositionId().value())
        ));
    }

    private void publishMessage(SellOrderCommand command, OpenPosition positionToSell){
        String routingKey = newOrdersQueueName(ListedCompanyTicker.of(positionToSell.getTicker().value()));

        messagePublisher.publish(NEW_ORDERS_EXCHNAGE, routingKey, new SendSellOrderMessage(
                command.getOrderID(),
                positionToSell.getPositionId(),
                command.getClientID(),
                command.getOrderDate(),
                command.getExecutionPrice(),
                command.getQuantity(),
                positionToSell.getTicker().value(),
                OrderTypeId.sell()));
    }
}
