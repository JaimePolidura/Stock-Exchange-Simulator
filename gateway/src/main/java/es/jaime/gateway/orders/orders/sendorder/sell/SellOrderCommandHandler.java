package es.jaime.gateway.orders.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.messagePublisher.MessagePublisher;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderBody;
import es.jaime.gateway.orders.orders._shared.domain.OrderClientId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import es.jaime.gateway.trades._shared.domain.Trade;
import es.jaime.gateway.trades._shared.domain.TradeFinderService;
import es.jaime.gateway.trades._shared.domain.TradesRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.*;

@Service
public class SellOrderCommandHandler implements CommandHandler<SellOrderCommand> {
    private final TradesRepository tradesRepository;
    private final OrdersRepository ordersRepository;
    private final MessagePublisher messagePublisher;
    private final TradeFinderService tradeFinderService;

    public SellOrderCommandHandler(TradesRepository tradesRepository, OrdersRepository ordersRepository,
                                   MessagePublisher messagePublisher, TradeFinderService tradeFinderService) {
        this.tradesRepository = tradesRepository;
        this.ordersRepository = ordersRepository;
        this.messagePublisher = messagePublisher;
        this.tradeFinderService = tradeFinderService;
    }

    @Override
    public void handle(SellOrderCommand command) {
        //If it is not found, the service will throw an exception
        Trade tradeToSell = tradeFinderService.find(command.getTradeId());

        ensureOwnsTheTrade(tradeToSell, command.getClientID());
        ensureCorrectQuantity(tradeToSell, command.getQuantity());

        ordersRepository.save(new Order(
                command.getOrderID(),
                command.getClientID(),
                command.getOrderDate(),
                OrderTypeId.of(2),
                OrderBody.of(Map.of(
                        "executionPrice", command.getExecutionPrice(),
                        "ticker", tradeToSell.getTicker().value(),
                        "quantity", command.getQuantity()
                ))
        ));

        publishMessage(command, tradeToSell);
    }

    private void ensureOwnsTheTrade(Trade trade, OrderClientId clientID){
        if(!trade.getClientId().value().equalsIgnoreCase(clientID.value())){
            throw new NotTheOwner("You are not the owner of that trade");
        }
    }

    private void ensureCorrectQuantity(Trade trade,  int quantity){
        if(quantity > trade.getQuantity().value()){
            throw new IllegalQuantity("You cant sell more than you have");
        }
    }

    private void publishMessage(SellOrderCommand command, Trade tradeToSell){
        String routingKey = newOrdersQueueName(ListedCompanyTicker.of(tradeToSell.getTicker().value()));

        messagePublisher.publish(NEW_ORDERS_EXCHNAGE, routingKey, new SendSellOrderMessage(
                command.getOrderID(),
                tradeToSell.getTradeId(),
                command.getClientID(),
                command.getOrderDate(),
                command.getExecutionPrice(),
                command.getQuantity(),
                tradeToSell.getTicker().value(),
                OrderTypeId.of(2)));
    }
}
