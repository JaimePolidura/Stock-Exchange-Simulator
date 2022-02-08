package es.jaime.gateway._shared.infrastrocture.exchanges.ordresrestorer;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class ExchangeStarted extends DomainEvent {
    @Getter private String ticker;
    @Getter private String exchangeName;

    @Override
    public ExchangeStarted fromPrimitives(Map<String, Object> primitives) {
        Map<String, Object> body = (Map<String, Object>) primitives.get("body");

        return new ExchangeStarted(
                String.valueOf(body.get("ticker")),
                String.valueOf(body.get("exchangeName"))
        );
    }

    @Override
    public EventName eventName() {
        return EventName.EXCHANGE_STARTED;
    }
}
