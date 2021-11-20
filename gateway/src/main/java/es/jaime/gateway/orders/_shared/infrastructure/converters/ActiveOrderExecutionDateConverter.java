package es.jaime.gateway.orders._shared.infrastructure.converters;

import es.jaime.gateway.orders._shared.domain.ActiveOrderExecutionPrice;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActiveOrderExecutionDateConverter implements AttributeConverter<Double, ActiveOrderExecutionPrice> {
    @Override
    public ActiveOrderExecutionPrice convertToDatabaseColumn(Double toConvert) {
        return ActiveOrderExecutionPrice.of(toConvert);
    }

    @Override
    public Double convertToEntityAttribute(ActiveOrderExecutionPrice toConvert) {
        return toConvert.value();
    }
}
