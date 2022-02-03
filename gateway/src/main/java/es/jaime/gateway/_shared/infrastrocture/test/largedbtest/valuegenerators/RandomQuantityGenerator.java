package es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators;

import org.springframework.stereotype.Service;

@Service
public class RandomQuantityGenerator {
    public int get(int max){
        return (int) (Math.random() * max + 1);
    }
}
