package es.jaime.gateway.orders.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messages.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.orders._shared.domain.orderbody.OrderBodySell;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import es.jaime.gateway.positions.open._shared.domain.OpenPosition;
import es.jaime.gateway.positions.open._shared.domain.OpenPositionFinder;
import org.springframework.stereotype.Service;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Service
public class SellOrderCommandHandler implements CommandHandler<SellOrderCommand> {
    private final OrdersRepository ordersRepository;
    private final MessagePublisher messagePublisher;
    private final OpenPositionFinder openPositionFinder;

    public SellOrderCommandHandler(OrdersRepository ordersRepository, MessagePublisher messagePublisher,
                                   OpenPositionFinder openPositionFinder) {
        this.ordersRepository = ordersRepository;
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

    private void ensureCorrectQuantity(OpenPosition positionToSell, int quantity){
        if(quantity > positionToSell.getQuantity().value()){
            throw new IllegalQuantity("You cant sell more than you have");
        }
    }

    private void saveToRepository(SellOrderCommand command, OpenPosition positionToSell){
        this.ordersRepository.save(new Order(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderTypeId.sell(),
                OrderBodySell.of(
                        command.getPositionId(),
                        command.getQuantity(),
                        command.getExecutionPrice(),
                        positionToSell.getTicker().value()
                )
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
