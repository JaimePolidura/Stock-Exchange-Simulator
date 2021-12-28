package es.jaime.gateway.orders.sendorder.sell;

import es.jaime.gateway._shared.domain.command.CommandHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalQuantity;
import es.jaime.gateway._shared.domain.exceptions.NotTheOwner;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.messagePublisher.MessagePublisher;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.trades._shared.domain.Trade;
import es.jaime.gateway.trades._shared.domain.TradesRepository;
import org.springframework.stereotype.Service;

import static es.jaime.gateway.orders._shared.domain.OrderTypeValues.*;

@Service
public class SellOrderCommandHandler implements CommandHandler<SellOrderCommand> {
    private final TradesRepository tradesRepository;
    private final OrdersRepository ordersRepository;
    private final MessagePublisher messagePublisher;

    public SellOrderCommandHandler(TradesRepository tradesRepository, OrdersRepository ordersRepository,
                                   MessagePublisher messagePublisher) {
        this.tradesRepository = tradesRepository;
        this.ordersRepository = ordersRepository;
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void handle(SellOrderCommand command) {
        Trade tradeToSell = tradesRepository.findByTradeId(command.getTradeId())
                .orElseThrow(() -> new ResourceNotFound("Trade not found for that Id"));;

        ensureOwnsTheTrade(tradeToSell, command.getClientID());
        ensureCorrectQuantity(tradeToSell, command.getQuantity());

        ordersRepository.save(new Order(
                command.getOrderID(),
                OrderTicker.of(tradeToSell.ticker().value()),
                command.getClientID(),
                command.getOrderDate(),
                command.getQuantity(),
                OrderType.of(SELL),
                command.getExecutionPrice()
        ));

        messagePublisher.publish(new SendSellOrderMessage(
                command.getOrderID(),
                tradeToSell.tradeId(),
                command.getClientID(),
                command.getOrderDate(),
                command.getExecutionPrice(),
                command.getQuantity(),
                OrderTicker.of(tradeToSell.ticker().value()),
                OrderType.of(SELL)
        ));
    }

    private void ensureOwnsTheTrade(Trade trade, OrderClientID clientID){
        if(!trade.clientId().value().equalsIgnoreCase(clientID.value())){
            throw new NotTheOwner("You are not the owner of that trade");
        }
    }

    private void ensureCorrectQuantity(Trade trade, OrderQuantity quantity){
        if(quantity.value() > trade.quantity().value()){
            throw new IllegalQuantity("You cant sell more than you have");
        }
    }
}
