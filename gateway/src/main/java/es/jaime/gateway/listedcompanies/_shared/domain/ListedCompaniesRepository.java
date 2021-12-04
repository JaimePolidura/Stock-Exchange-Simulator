package es.jaime.gateway.listedcompanies._shared.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ListedCompaniesRepository {
    Optional<ListedCompany> findByTicker(ListedCompanyTicker ticker);
}
