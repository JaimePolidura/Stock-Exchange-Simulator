package es.jaime.gateway.listedcompanies.getlalllistedcompanies;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAllListedCompaniesQueryHandler implements QueryHandler<GetAllListedCompaniesQuery, GetAllListedCompaniesQueryResponse> {
    private final ListedCompaniesRepository repository;

    @Override
    public GetAllListedCompaniesQueryResponse handle(GetAllListedCompaniesQuery query) {
        return GetAllListedCompaniesQueryResponse.create(repository.findAll());
    }
}
