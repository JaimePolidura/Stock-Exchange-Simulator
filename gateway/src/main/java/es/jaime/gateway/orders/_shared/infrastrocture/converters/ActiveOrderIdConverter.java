package es.jaime.gateway.orders._shared.infrastrocture.converters;

import es.jaime.gateway.orders._shared.domain.ActiveOrderID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter
public class ActiveOrderIdConverter implements AttributeConverter<String, ActiveOrderID> {
    @Override
    public ActiveOrderID convertToDatabaseColumn(String toConvert) {
        return ActiveOrderID.of(UUID.fromString(toConvert));
    }

    @Override
    public String convertToEntityAttribute(ActiveOrderID toConvert) {
        return toConvert.value().toString();
    }
}
