package es.jaime.gateway.listedcompanies._shared.infrastructure.converters;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompnayTicker;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ListedCompanyTickerConverter implements AttributeConverter<ListedCompnayTicker, String> {
    @Override
    public String convertToDatabaseColumn(ListedCompnayTicker listedCompnayTicker) {
        return listedCompnayTicker.value();
    }

    @Override
    public ListedCompnayTicker convertToEntityAttribute(String toConvert) {
        return ListedCompnayTicker.of(toConvert);
    }
}
