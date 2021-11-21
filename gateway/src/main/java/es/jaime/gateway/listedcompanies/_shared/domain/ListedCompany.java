package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;

import java.util.HashMap;
import java.util.Map;

public final class ListedCompany extends Aggregate {
    private ListedCompanyTicker ticker;

    public ListedCompany(ListedCompanyTicker ticker) {
        this.ticker = ticker;
    }

    public ListedCompany () {}

    public ListedCompanyTicker ticker() {
        return ticker;
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("ticker", ticker.value());
        }};
    }

    @Override
    public ListedCompany fromPrimitives(Map<String, Object> values) {
        return new ListedCompany(ListedCompanyTicker.of(String.valueOf(values.get("ticker"))));
    }
}
