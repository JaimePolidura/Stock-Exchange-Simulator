package es.jaimetruman.exchangedistribuitor.distributor;


import es.jaimetruman._shared.Order;
import es.jaimetruman.exchangedistribuitor.gateway.GatewayExchangeDistributor;
import es.jaimetruman.exchangedistribuitor._shared.RawOrder;
import es.jaimetruman.exchanges.exchange.Exchange;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

public final class ExchangeDistributor implements Runnable{
    private final GatewayExchangeDistributor gateway;
    private final ActiveExchangesRepository exchanges;

    public ExchangeDistributor(GatewayExchangeDistributor gateway, ActiveExchangesRepository exchanges) {
        this.gateway = gateway;
        this.exchanges = exchanges;
    }

    @SneakyThrows
    @Override
    public void run() {
        while(true){
            List<Order> orderList = gateway.dequeueAll();

            orderList.forEach(this::distributeOrder);

            Thread.sleep(100);
        }
    }

    private void distributeOrder(Order order){
        Optional<Exchange> exchange = exchanges.findByTicker(order.getTicker());

        if(exchange.isPresent()){
            exchange.get().enqueueOrder(order);
        }else{
            Exchange newExchange = startNewExchange(order.getTicker());

            newExchange.enqueueOrder(order);
        }
    }

    private Exchange startNewExchange(String ticker){
        Exchange newExchange = new Exchange(ticker);
        this.exchanges.add(newExchange);

        return newExchange;
    }
}
