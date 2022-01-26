package es.jaime.exchange.domain.models.messages;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface Message extends Serializable {
    MessageTypes type();
    MessageName name();

    Map<String, Object> body();

    default Map<String, Object> meta(){
        return new HashMap<>();
    }

    default UUID id(){
        return UUID.randomUUID();
    }

    default LocalDateTime date(){
        return LocalDateTime.now();
    }

    default Map<String, Object> toPrimitives(){
        return Map.of(
                "id", id().toString(),
                "date", date().toString(),
                "type", type().name(),
                "name", name().getName(),
                "body", body(),
                "meta", meta()
        );
    }
}
