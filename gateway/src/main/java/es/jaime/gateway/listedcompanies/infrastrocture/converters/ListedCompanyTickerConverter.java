package es.jaime.gateway.listedcompanies.infrastrocture.converters;

import es.jaime.gateway.listedcompanies.domain.ListedCompnayTicker;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public final class ListedCompanyTickerConverter implements AttributeConverter<String, ListedCompnayTicker> {
    @Override
    public ListedCompnayTicker convertToDatabaseColumn(String value) {
        return value == null ? null : ListedCompnayTicker.of(value);
    }

    @Override
    public String convertToEntityAttribute(ListedCompnayTicker value) {
        return value == null ? null : value.value();
    }
}
