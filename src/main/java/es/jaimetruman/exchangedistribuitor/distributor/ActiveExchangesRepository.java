package es.jaimetruman.exchangedistribuitor.distributor;

import es.jaimetruman.exchanges.exchange.Exchange;

import java.util.Optional;

public interface ActiveExchangesRepository {
    boolean exitsByTicker(String ticker);

    void add(Exchange exchange);

    Optional<Exchange> findByTicker(String ticker);
}
