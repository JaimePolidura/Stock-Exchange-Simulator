package es.jaime.exchange.domain.models.messages.messages;

import es.jaime.exchange.domain.models.messages.Message;
import es.jaime.exchange.domain.models.messages.MessageNames;
import es.jaime.exchange.domain.models.messages.MessageTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ExchangeStarted implements Message {
    @Getter private final String ticker;

    @Override
    public MessageTypes type() {
        return MessageTypes.EVENT;
    }

    @Override
    public MessageNames name() {
        return MessageNames.EXCHANGE_STARTED;
    }

    @Override
    public Map<String, Object> body() {
        return Map.of("tikcer", ticker);
    }
}
