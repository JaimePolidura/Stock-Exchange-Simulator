package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.hibernate.SessionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional("gateway-transaction-manager")
public class ListedCompaniesRepositoryMySQL extends HibernateRepository<ListedCompany> implements ListedCompaniesRepository, CommandLineRunner {
    private final Map<ListedCompanyTicker, ListedCompany> indexedCacheByTicker;
    private final List<ListedCompany> allListedCompaniesCache;

    public ListedCompaniesRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, ListedCompany.class);

        this.indexedCacheByTicker = new HashMap<>();
        this.allListedCompaniesCache = new ArrayList<>();
    }

    @Override
    public Optional<ListedCompany> findByTicker(ListedCompanyTicker ticker) {
        return Optional.ofNullable(indexedCacheByTicker.get(ticker));
    }

    @Override
    public List<ListedCompany> findAll() {
        return allListedCompaniesCache;
    }

    /**
     * This method will setup the cache
     */
    @Override
    public void run(String... args) throws Exception {
        addIntoCache(super.all());
    }

    private void addIntoCache(List<ListedCompany> allListedCompanies){
        this.allListedCompaniesCache.addAll(allListedCompanies);

        for (var listedCompany : allListedCompanies) {
            this.indexedCacheByTicker.put(listedCompany.ticker(), listedCompany);
        }
    }
}
