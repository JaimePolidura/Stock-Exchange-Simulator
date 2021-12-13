package es.jaime.exchange.application;

import es.jaime.exchange.domain.MatchingEngine;
import es.jaime.exchange.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class MatchingEngineImpl implements MatchingEngine, Runnable {
    @Override
    public void enqueue(Order order) {

    }

    @Override
    public void run() {

    }
}
