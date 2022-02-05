package es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators;

import org.springframework.stereotype.Service;

@Service
public class RandomPriceGenerator {
    public double get(int max){
        return Math.random() * max + 1;
    }
}
