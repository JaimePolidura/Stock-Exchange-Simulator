package es.jaime.gateway.trades._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class Trade extends Aggregate {
    @Getter private TradeId tradeId;
    @Getter private TradeClientId clientId;
    @Getter private TradeTicker ticker;
    @Getter private TradeOpeningPrice openingPrice;
    @Getter private TradeOpeningDate openingDate;
    @Getter private TradeQuantity quantity;

    public Trade () {}

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "tradeId", tradeId.value(),
                "clientId", clientId.value(),
                "ticker", ticker.value(),
                "openingPrice", openingPrice.value(),
                "openingDate", openingDate.value(),
                "quantity", quantity.value()
        );
    }

    @Override
    public Trade fromPrimitives(Map<String, Object> values) {
        return new Trade(
                TradeId.of(String.valueOf(values.get("tradeId"))),
                TradeClientId.of(String.valueOf(values.get("clientId"))),
                TradeTicker.of(String.valueOf(values.get("ticker"))),
                TradeOpeningPrice.of(Double.parseDouble(String.valueOf(values.get("openingPrice")))),
                TradeOpeningDate.of(String.valueOf(values.get("openingDate"))),
                TradeQuantity.of(Integer.parseInt(String.valueOf(values.get("quantity"))))
        );
    }
}
