package es.jaime.gateway.listedcompanies.getlalllistedcompanies;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAllListedCompaniesQueryHandler implements QueryHandler<GetAllListedCompaniesQuery, GetAllListedCompaniesQueryResponse> {
    private final ListedCompaniesRepository repository;

    public GetAllListedCompaniesQueryHandler(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    @Override
    public GetAllListedCompaniesQueryResponse handle(GetAllListedCompaniesQuery query) {
        return GetAllListedCompaniesQueryResponse.create(repository.findAll());
    }
}
