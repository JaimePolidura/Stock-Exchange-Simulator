package es.jaime.exchange.domain.models.messages;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    String name();

    Map<String, Object> body();

    default Map<String, Object> meta(){
        return new HashMap<>();
    }

    default Map<String, Object> toPrimitives(){
        return Map.of(
                "type", type().name(),
                "name", name(),
                "body", body(),
                "meta", meta()
        );
    }
}
