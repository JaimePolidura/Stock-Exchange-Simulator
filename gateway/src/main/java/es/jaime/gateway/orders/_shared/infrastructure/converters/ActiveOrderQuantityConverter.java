package es.jaime.gateway.orders._shared.infrastructure.converters;

import es.jaime.gateway.orders._shared.domain.ActiveOrderQuantity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActiveOrderQuantityConverter implements AttributeConverter<Integer, ActiveOrderQuantity> {
    @Override
    public ActiveOrderQuantity convertToDatabaseColumn(Integer toConvert) {
        return ActiveOrderQuantity.of(toConvert);
    }

    @Override
    public Integer convertToEntityAttribute(ActiveOrderQuantity toConvert) {
        return toConvert.value();
    }
}
