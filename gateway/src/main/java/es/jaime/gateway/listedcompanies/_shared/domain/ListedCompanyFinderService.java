package es.jaime.gateway.listedcompanies._shared.domain;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListedCompanyFinderService {
    private final ListedCompaniesRepository repository;

    public ListedCompanyFinderService(ListedCompaniesRepository repository) {
        this.repository = repository;
    }

    public ListedCompany find(ListedCompanyTicker ticker){
        return repository.findByTicker(ticker)
                .orElseThrow(() -> new ResourceNotFound("Company not listed"));
    }

    public List<ListedCompany> all(){
        return repository.findAll();
    }
}
