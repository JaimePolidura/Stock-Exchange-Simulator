package es.jaime.exchange.application;

import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import es.jaime.exchange.domain.models.Order;
import es.jaime.exchange.domain.services.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradeProcessorImpl implements TradeProcessor {
    private final QueuePublisher queuePublisher;
    private final ExchangeConfiguration configuration;
    private final MatchingPriceEngine matchingPriceEngine;

    public TradeProcessorImpl(QueuePublisher queuePublisher, ExchangeConfiguration configuration, MatchingPriceEngine matchingPriceEngine) {
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

            QueueMessage queueMessage = ExecutedOrderQueueMessage.create(buyOrder, sellOrder, priceMatch, quantityStockTradeMatch);

            System.out.println("Order executed " + queueMessage.toJson());

            queuePublisher.enqueue(
                    configuration.executedOrdersExchangeName(),
                    configuration.executedOrdersQueueName(),
                    queueMessage
            );

        }catch (Exception ex){
            ex.printStackTrace();

            throwUnProcessableTrade(buyOrder, sellOrder);
        }
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
