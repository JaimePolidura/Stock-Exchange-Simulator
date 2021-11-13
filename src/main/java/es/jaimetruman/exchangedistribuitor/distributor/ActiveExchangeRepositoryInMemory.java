package es.jaimetruman.exchangedistribuitor.distributor;

import es.jaimetruman.exchanges.exchange.Exchange;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ActiveExchangeRepositoryInMemory implements ActiveExchangesRepository {
    private final Map<String, Exchange> exchanges;

    public ActiveExchangeRepositoryInMemory(){
        this.exchanges = new HashMap<>();
    }

    @Override
    public boolean exitsByTicker(String ticker) {
        return exchanges.get(ticker) != null;
    }

    @Override
    public void add(Exchange exchange) {
        this.exchanges.put(exchange.getTicker(), exchange);
    }

    @Override
    public Optional<Exchange> findByTicker(String ticker) {
        return  Optional.ofNullable(exchanges.get(ticker));
    }
}
