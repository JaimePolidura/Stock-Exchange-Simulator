package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional("gateway-transaction-manager")
public class ListedCompaniesRepositoryMySQL extends HibernateRepository<ListedCompany> implements ListedCompaniesRepository {
    public ListedCompaniesRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, ListedCompany.class);
    }

    @Override
    public Optional<ListedCompany> findByTicker(ListedCompanyTicker ticker) {
        return  byId(ticker);
    }
}
