package es.jaime.gateway._shared.domain.exchange;

import java.util.Map;

public interface ExchangeDescriptor {
    String name(String ticker);
    Map<String, Object> properties(String ticker, String exchangeName);
}
