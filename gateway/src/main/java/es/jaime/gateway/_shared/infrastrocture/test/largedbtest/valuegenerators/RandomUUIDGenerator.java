package es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RandomUUIDGenerator {
    public String get(){
        return UUID.randomUUID().toString();
    }
}
