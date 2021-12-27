package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;

import java.util.HashMap;
import java.util.Map;

public final class ListedCompany extends Aggregate {
    private ListedCompanyTicker ticker;
    private ListedCompanyName name;

    public ListedCompany(ListedCompanyTicker ticker, ListedCompanyName name) {
        this.ticker = ticker;
        this.name = name;
    }

    public ListedCompany () {}

    public ListedCompanyTicker ticker() {
        return ticker;
    }

    public ListedCompanyName name() {
        return name;
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("ticker", ticker.value());
            put("name", name.value());
        }};
    }

    @Override
    public ListedCompany fromPrimitives(Map<String, Object> values) {
        return new ListedCompany(
                ListedCompanyTicker.of(String.valueOf(values.get("ticker"))),
                ListedCompanyName.of(String.valueOf(values.get("name")))
        );
    }
}
