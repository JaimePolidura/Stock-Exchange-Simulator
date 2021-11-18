package es.jaime.gateway.orders._shared.infrastrocture.converters;

import es.jaime.gateway.orders._shared.domain.ActiveOrderDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActiveOrderDateConverter implements AttributeConverter<String, ActiveOrderDate> {
    @Override
    public ActiveOrderDate convertToDatabaseColumn(String toConvert) {
        return ActiveOrderDate.of(toConvert);
    }

    @Override
    public String convertToEntityAttribute(ActiveOrderDate toConvert) {
        return toConvert.value().toString();
    }
}
