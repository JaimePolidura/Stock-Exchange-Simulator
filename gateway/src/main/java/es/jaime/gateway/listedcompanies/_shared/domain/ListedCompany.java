package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;

import java.util.HashMap;
import java.util.Map;

public final class ListedCompany extends Aggregate {
    private ListedCompanyTicker ticker;
    private ListedCompanyName name;
    private ListedCompanyCurrencyCode currencyCode;
    private ListedCompanyCurrencySymbol currencySymbol;

    public ListedCompany(ListedCompanyTicker ticker, ListedCompanyName name, ListedCompanyCurrencyCode currencyCode,
                         ListedCompanyCurrencySymbol currencySymbol) {
        this.ticker = ticker;
        this.name = name;
        this.currencyCode = currencyCode;
        this.currencySymbol = currencySymbol;
    }

    public ListedCompany () {}

    public ListedCompanyTicker ticker() {
        return ticker;
    }

    public ListedCompanyName name() {
        return name;
    }

    public ListedCompanyCurrencyCode currencyCode() {
        return currencyCode;
    }

    public ListedCompanyCurrencySymbol currencySymbol() {
        return currencySymbol;
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>() {{
            put("ticker", ticker.value());
            put("name", name.value());
            put("currencyCode", currencyCode.value());
            put("currencySymbol", currencySymbol.value());
        }};
    }

    @Override
    public ListedCompany fromPrimitives(Map<String, Object> values) {
        return new ListedCompany(
                ListedCompanyTicker.of(String.valueOf(values.get("ticker"))),
                ListedCompanyName.of(String.valueOf(values.get("name"))),
                ListedCompanyCurrencyCode.of(String.valueOf(values.get("currencyCode"))),
                ListedCompanyCurrencySymbol.of(String.valueOf(values.get("currencySymbol")))
        );
    }
}
