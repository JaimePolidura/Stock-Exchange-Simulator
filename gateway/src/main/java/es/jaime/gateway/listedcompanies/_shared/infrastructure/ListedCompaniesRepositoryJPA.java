package es.jaime.gateway.listedcompanies._shared.infrastructure;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompnayTicker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ListedCompaniesRepositoryJPA extends JpaRepository<ListedCompany, ListedCompnayTicker> {
    Boolean existsByTicker(ListedCompnayTicker ticker);
}
