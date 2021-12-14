package es.jaime.exchange.application;

import es.jaime.exchange.domain.Order;
import es.jaime.exchange.domain.TradeProcessor;
import org.springframework.stereotype.Service;

@Service
public class TradeProcessorImpl implements TradeProcessor {
    @Override
    public void process(Order buyOrder, Order sellOrder) throws Exception {
        //TODO
    }
}
