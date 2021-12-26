package es.jaime.exchange.application;

import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.models.messages.ExecutedOrderMessage;
import es.jaime.exchange.domain.services.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class TradeProcessorImpl implements TradeProcessor {
    private final MessagePublisher queuePublisher;
    private final ExchangeConfiguration configuration;
    private final MatchingPriceEngine matchingPriceEngine;

    public TradeProcessorImpl(MessagePublisher queuePublisher, ExchangeConfiguration configuration, MatchingPriceEngine matchingPriceEngine) {
        this.queuePublisher = queuePublisher;
        this.configuration = configuration;
        this.matchingPriceEngine = matchingPriceEngine;
    }

    @Override
    public void process(Order buyOrder, Order sellOrder) {
        try{
            double priceMatch = matchingPriceEngine.getPriceMatch(buyOrder, sellOrder);
            int quantityStockTradeMatch = getQuantityStockTradeMatch(buyOrder, sellOrder);

            buyOrder.decreaseQuantityBy(quantityStockTradeMatch);
            sellOrder.decreaseQuantityBy(quantityStockTradeMatch);

            publishMessage(buyOrder, priceMatch, quantityStockTradeMatch);
            publishMessage(sellOrder, priceMatch, quantityStockTradeMatch);

        }catch (Exception ex){
            ex.printStackTrace();

            throwUnProcessableTrade(buyOrder, sellOrder);
        }
    }

    private void publishMessage(Order order, double priceMatch, int quantity){
        this.queuePublisher.publish(
                configuration.executedOrdersExchangeName(),
                configuration.executedOrdersQueueName(),
                new ExecutedOrderMessage(order.getOrderId(), order.getClientId(), order.getTicker(),
                        priceMatch, quantity, LocalDateTime.now().toString(), order.getType())
        );
    }

    private int getQuantityStockTradeMatch(Order buyOrder, Order sellOrder){
        return buyOrder.getQuantity() == sellOrder.getQuantity() ?
                buyOrder.getQuantity() :
                Math.min(buyOrder.getQuantity(), sellOrder.getQuantity());
    }

    private void throwUnProcessableTrade(Order buyOrder, Order sellOrder){
        var exceptionSet = Set.of(new UnprocessableTrade(buyOrder), new UnprocessableTrade(sellOrder));

        for (var domainException : exceptionSet)
            throw domainException;
    }
}
