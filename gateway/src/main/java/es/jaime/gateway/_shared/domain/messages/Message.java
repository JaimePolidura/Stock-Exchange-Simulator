package es.jaime.gateway._shared.domain.messages;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *{
 *   "type": "command",
 *   "name": "new-buy-order",
 *   "body": {
 *     "clientId": "jaime",
 *     "ticker": "AMZN",
 *     "executionPrice": 12
 *   },
 *   "meta": {}
 * }
 */
public interface Message extends Serializable {
    MessageTypes type();
    MessageNames name();

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
                "now", date().toString(),
                "type", type().name(),
                "name", name().getName(),
                "body", body(),
                "meta", meta()
        );
    }
}
