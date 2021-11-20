package es.jaime.gateway.orders._shared.infrastructure.converters;

import es.jaime.gateway.orders._shared.domain.ActiveOrderClientID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter
public class ActiveOrderClientIdConverter implements AttributeConverter<String, ActiveOrderClientID> {
    @Override
    public ActiveOrderClientID convertToDatabaseColumn(String toConvert) {
        return ActiveOrderClientID.of(UUID.fromString(toConvert));
    }

    @Override
    public String convertToEntityAttribute(ActiveOrderClientID toConvert) {
        return toConvert.value().toString();
    }
}
