package es.jaime.gateway.orders._shared.infrastructure.converters;

import es.jaime.gateway.orders._shared.domain.ActiveOrderTicker;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActiveOrderTickerConverter implements AttributeConverter<String, ActiveOrderTicker> {
    @Override
    public ActiveOrderTicker convertToDatabaseColumn(String toConvert) {
        return ActiveOrderTicker.of(toConvert);
    }

    @Override
    public String convertToEntityAttribute(ActiveOrderTicker toConvert) {
        return toConvert.value();
    }
}
