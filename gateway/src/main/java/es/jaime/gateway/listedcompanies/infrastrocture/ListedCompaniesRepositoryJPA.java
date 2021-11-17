package es.jaime.gateway.listedcompanies.infrastrocture;

import es.jaime.gateway.listedcompanies.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies.domain.ListedCompany;
import org.checkerframework.common.util.report.qual.ReportUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Si no funciona, hacer overrido del metodo de existsByTicker aqui
@Repository
public interface ListedCompaniesRepositoryJPA extends ListedCompaniesRepository, JpaRepository<ListedCompany, String> {
}
