package es.jaime.exchange.application;

import es.jaime.exchange.domain.events.EventBus;
import es.jaime.exchange.domain.events.ExceptionOccurredEvent;
import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import es.jaime.exchange.domain.models.messages.BuyOrderExecutedMessage;
import es.jaime.exchange.domain.models.messages.EventNames;
import es.jaime.exchange.domain.models.messages.SellOrderExecutedMessage;
import es.jaime.exchange.domain.models.orders.BuyOrder;
import es.jaime.exchange.domain.models.orders.Order;
import es.jaime.exchange.domain.models.orders.OrderType;
import es.jaime.exchange.domain.models.orders.SellOrder;
import es.jaime.exchange.domain.services.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradeProcessorImpl implements TradeProcessor {
    private final MessagePublisher queuePublisher;
    private final ExchangeConfiguration configuration;
    private final MatchingPriceEngine matchingPriceEngine;
    private final EventBus eventBus;

    public TradeProcessorImpl(MessagePublisher queuePublisher, ExchangeConfiguration configuration,
                              MatchingPriceEngine matchingPriceEngine, EventBus eventBus) {
        this.queuePublisher = queuePublisher;
        this.configuration = configuration;
        this.matchingPriceEngine = matchingPriceEngine;
        this.eventBus = eventBus;
    }

    @Override
    public void process(BuyOrder buyOrder, SellOrder sellOrder) {
        try{
            double priceMatch = matchingPriceEngine.getPriceMatch(buyOrder, sellOrder);
            int quantityStockTradeMatch = getQuantityStockTradeMatch(buyOrder, sellOrder);

            buyOrder.decreaseQuantityBy(quantityStockTradeMatch);
            sellOrder.decreaseQuantityBy(quantityStockTradeMatch);

            System.out.println("Order executed");

            publishBuyOrderExecutedMessage(buyOrder, priceMatch, quantityStockTradeMatch);
            publishSellOrderExecutedMessage(sellOrder, priceMatch, quantityStockTradeMatch, buyOrder.getTicker());

        }catch (Exception ex){
            ex.printStackTrace();

            handleException(buyOrder, sellOrder);
        }
    }

    private void publishBuyOrderExecutedMessage(BuyOrder order, double priceMatch, int quantity){
        this.queuePublisher.publish(
                configuration.eventsExchangeName(),
                configuration.eventsExchangeName() + ".*." + EventNames.ORDER_EXECUTED_BUY.getName(),
                new BuyOrderExecutedMessage(order.getOrderId(), order.getClientId(), order.getTicker(), priceMatch,
                        quantity, order.getDate(), OrderType.BUY)
        );
    }

    private void publishSellOrderExecutedMessage(SellOrder order, double priceMatch, int quantity, String ticker){
        this.queuePublisher.publish(
                configuration.eventsExchangeName(),
                configuration.eventsExchangeName() + ".*." + EventNames.ORDER_EXECUTED_SELL.getName(),
                new SellOrderExecutedMessage(order.getOrderId(), order.getClientId(), order.getTradeId(),
                        priceMatch, quantity, order.getDate(), OrderType.SELL, ticker)
        );
    }

    private int getQuantityStockTradeMatch(Order buyOrder, Order sellOrder){
        return buyOrder.getQuantity() == sellOrder.getQuantity() ?
                buyOrder.getQuantity() :
                Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
    }

    private void handleException(Order buyOrder, Order sellOrder){
        var exceptionSet = Set.of(new UnprocessableTrade(buyOrder), new UnprocessableTrade(sellOrder));

        for (var domainException : exceptionSet)
            eventBus.publish(new ExceptionOccurredEvent(domainException));
    }
}
