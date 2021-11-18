package es.jaime.gateway.listedcompanies.infrastrocture.converters;

import es.jaime.gateway.listedcompanies.domain.ListedCompnayTicker;

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
