package es.jaime.exchange.application;

import es.jaime.exchange.domain.models.events.EventBus;
import es.jaime.exchange.domain.models.events.ExceptionOccurred;
import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import es.jaime.exchange.domain.models.events.OrderMessagePublished;
import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.messages.messages.BuyOrderExecutedMessage;
import es.jaime.exchange.domain.models.messages.messages.SellOrderExecutedMessage;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.ExecutionOrder;
import es.jaime.exchange.domain.models.orders.OrderType;
import es.jaime.exchange.domain.models.orders.SellOrder;
import es.jaime.exchange.domain.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TradeProcessorImpl implements TradeProcessor {
    private final MessagePublisher queuePublisher;
    private final ExchangeConfiguration configuration;
    private final MatchingPriceEngine matchingPriceEngine;
    private final EventBus eventBus;

    @Override
    public void process(BuyOrder buyOrder, SellOrder sellOrder) {
        try{
            double priceMatch = matchingPriceEngine.getPriceMatch(buyOrder, sellOrder);
            int quantityStockTradeMatch = getQuantityStockTradeMatch(buyOrder, sellOrder);

            buyOrder.decreaseQuantityBy(quantityStockTradeMatch);
            sellOrder.decreaseQuantityBy(quantityStockTradeMatch);

            publishBuyOrderExecutedMessage(buyOrder, priceMatch, quantityStockTradeMatch);
            publishSellOrderExecutedMessage(sellOrder, priceMatch, quantityStockTradeMatch, buyOrder.getTicker());

            this.eventBus.publish(new OrderMessagePublished(buyOrder.getOrderId(), sellOrder.getOrderId()));
        }catch (Exception ex){
            ex.printStackTrace();

            handleException(buyOrder, sellOrder);
        }
    }

    private void publishBuyOrderExecutedMessage(BuyOrder order, double priceMatch, int quantity){
        this.queuePublisher.publish(
                configuration.eventsExchangeName(),
                String.format(configuration.eventsRoutingKey(), MessageName.EXECUTED_BUY_ORDER.getName()),
                new BuyOrderExecutedMessage(order.getOrderId(), order.getClientId(), order.getTicker(), priceMatch,
                        quantity, order.getDate(), OrderType.BUY, UUID.randomUUID().toString())
        );
    }

    private void publishSellOrderExecutedMessage(SellOrder order, double priceMatch, int quantity, String ticker){
        this.queuePublisher.publish(
                configuration.eventsExchangeName(),
                String.format(configuration.eventsRoutingKey(), MessageName.EXECUTED_SELL_ORDER.getName()),
                new SellOrderExecutedMessage(order.getOrderId(), order.getClientId(), order.getPositionId(),
                        priceMatch, quantity, order.getDate(), OrderType.SELL, ticker, UUID.randomUUID().toString())
        );
    }

    private int getQuantityStockTradeMatch(ExecutionOrder buyOrder, ExecutionOrder sellOrder){
        return buyOrder.getQuantity() == sellOrder.getQuantity() ?
                buyOrder.getQuantity() :
                Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
    }

    private void handleException(ExecutionOrder buyOrder, ExecutionOrder sellOrder){
        var exceptionSet = Set.of(new UnprocessableTrade(buyOrder), new UnprocessableTrade(sellOrder));

        for (var domainException : exceptionSet)
            eventBus.publish(new ExceptionOccurred(domainException));
    }
}
