package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;

import java.util.HashMap;
import java.util.Map;

public final class ListedCompany extends Aggregate {
    private ListedCompanyTicker ticker;
    private ListedCompanyName name;
    private ListedCompanyCurrency currency;

    public ListedCompany(ListedCompanyTicker ticker, ListedCompanyName name, ListedCompanyCurrency currency) {
        this.ticker = ticker;
        this.name = name;
        this.currency = currency;
    }

    public ListedCompany () {}

    public ListedCompanyTicker ticker() {
        return ticker;
    }

    public ListedCompanyName name() {
        return name;
    }

    public ListedCompanyCurrency currency() {
        return currency;
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("ticker", ticker.value());
            put("name", name.value());
            put("currency", currency.value());
        }};
    }

    @Override
    public ListedCompany fromPrimitives(Map<String, Object> values) {
        return new ListedCompany(
                ListedCompanyTicker.of(String.valueOf(values.get("ticker"))),
                ListedCompanyName.of(String.valueOf(values.get("name"))),
                ListedCompanyCurrency.of(String.valueOf(values.get("currency")))
        );
    }
}
