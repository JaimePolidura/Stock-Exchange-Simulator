package es.jaime.gateway._shared.infrastrocture.test.largedbtest.valuegenerators;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RandomDateGenerator {
    public String get(){
        return LocalDateTime.now().toString();
    }
}
