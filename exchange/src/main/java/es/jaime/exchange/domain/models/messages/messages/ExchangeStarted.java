package es.jaime.exchange.domain.models.messages.messages;

import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.models.messages.MessageName;
import es.jaime.exchange.domain.models.messages.MessageTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ExchangeStarted implements Message {
    @Getter private final String ticker;
    @Getter private final String exchangeName;

    @Override
    public MessageTypes type() {
        return MessageTypes.EVENT;
    }

    @Override
    public MessageName name() {
        return MessageName.EXCHANGE_STARTED;
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
            "tikcer", ticker,
            "exchangeName", exchangeName
        );
    }
}
