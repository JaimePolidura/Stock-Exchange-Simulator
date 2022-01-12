package es.jaime.gateway.listedcompanies._shared.domain;


import java.util.List;
import java.util.Optional;

public interface ListedCompaniesRepository {
    Optional<ListedCompany> findByTicker(ListedCompanyTicker ticker);

    List<ListedCompany> findAll();
}
