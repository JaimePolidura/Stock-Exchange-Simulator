package es.jaime.gateway.listedcompanies.getlalllistedcompanies;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public final class GetAllListedCompaniesQueryResponse implements QueryResponse {
    private final List<ListedCompanyQueryResponse> allListedCompanies;

    public static GetAllListedCompaniesQueryResponse create(List<ListedCompany> listedCompanies){
        return new GetAllListedCompaniesQueryResponse(listedCompanies.stream()
                .map(listedCompany -> new ListedCompanyQueryResponse(listedCompany.ticker().value(), listedCompany.name().value(),
                        listedCompany.currencyCode().value(), listedCompany.currencySymbol().value()))
                .collect(Collectors.toList()));
    }

    public GetAllListedCompaniesQueryResponse(List<ListedCompanyQueryResponse> allListedCompanies){
        this.allListedCompanies = allListedCompanies;
    }

    @AllArgsConstructor
    public static class ListedCompanyQueryResponse {
        @Getter private final String ticker;
        @Getter private final String name;
        @Getter private final String currencyCode;
        @Getter private final String currencySymbol;
    }
}
