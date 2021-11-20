package es.jaime.gateway.listedcompanies.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import es.jaime.gateway.listedcompanies.infrastrocture.converters.ListedCompanyTickerConverter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "listedcompanies")
public class ListedCompany extends Aggregate {
    @Id
    @Convert(converter = ListedCompanyTickerConverter.class)
    private ListedCompnayTicker ticker;

    public ListedCompany(ListedCompnayTicker ticker) {
        this.ticker = ticker;
    }

    public ListedCompany() {}

    public ListedCompnayTicker getTicker() {
        return ticker;
    }

    public void setTicker(ListedCompnayTicker ticker) {
        this.ticker = ticker;
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("ticker", ticker.value());
        }};
    }

    @Override
    public ListedCompany fromPrimitives(Map<String, Object> values) {
        return new ListedCompany(ListedCompnayTicker.of(String.valueOf(values.get("ticker"))));
    }
}
