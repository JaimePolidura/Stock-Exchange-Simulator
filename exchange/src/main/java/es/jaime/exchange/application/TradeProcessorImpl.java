package es.jaime.exchange.application;

import es.jaime.exchange.ExchangeConfiguration;
import es.jaime.exchange.domain.Order;
import es.jaime.exchange.domain.QueuePublisher;
import es.jaime.exchange.domain.TradeProcessor;
import es.jaime.exchange.domain.exceptions.UnprocessableTrade;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradeProcessorImpl implements TradeProcessor {
    private final QueuePublisher queuePublisher;
    private final ExchangeConfiguration configuration;

    public TradeProcessorImpl(QueuePublisher queuePublisher, ExchangeConfiguration configuration) {
        this.queuePublisher = queuePublisher;
        this.configuration = configuration;
    }

    @Override
    public void process(Order buyOrder, Order sellOrder) {
        try{
            double priceMatch = getPriceMatch(buyOrder, sellOrder);

            queuePublisher.enqueue(
                    configuration.executedOrdersExchangeName(),
                    configuration.executedOrdersQueueName(),
                    ExecutedOrderQueueMessage.create(buyOrder, sellOrder, priceMatch)
            );

        }catch (Exception ex){
            throwUnProcessableTrade(buyOrder, sellOrder);
        }
    }

    private double getPriceMatch(Order buyOrder, Order sellOrder){
        return buyOrder.getExecutionPrice();
    }

    private void throwUnProcessableTrade(Order buyOrder, Order sellOrder){
        var exceptionSet = Set.of(new UnprocessableTrade(buyOrder), new UnprocessableTrade(sellOrder));

        for (var domainException : exceptionSet)
            throw domainException;
    }
}
