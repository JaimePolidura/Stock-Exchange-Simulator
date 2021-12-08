package es.jaime.gateway.listedcompanies._shared.infrastructure.persistence;

import es.jaime.gateway._shared.infrastrocture.persistance.HibernateRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompaniesRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional("gateway-transaction-manager")
public class ListedCompaniesRepositoryMySQL extends HibernateRepository<ListedCompany> implements ListedCompaniesRepository {
    private final Map<ListedCompanyTicker, ListedCompany> indexedCacheByTicker;
    private List<ListedCompany> allListedCompaniesCache;

    public ListedCompaniesRepositoryMySQL(SessionFactory sessionFactory) {
        super(sessionFactory, ListedCompany.class);

        this.indexedCacheByTicker = new HashMap<>();
        this.allListedCompaniesCache = new ArrayList<>();
    }

    @Override
    public Optional<ListedCompany> findByTicker(ListedCompanyTicker ticker) {
        ensureCacheLoadedOrLoad();

        return Optional.ofNullable(indexedCacheByTicker.get(ticker));
    }

    @Override
    public List<ListedCompany> findAll() {
        ensureCacheLoadedOrLoad();

        return allListedCompaniesCache;
    }

    private void ensureCacheLoadedOrLoad(){
        boolean loaded = this.indexedCacheByTicker.size() != 0 && this.allListedCompaniesCache.size() != 0;

        if(!loaded){
            addAllListedCompaniesInCache();
        }
    }

    private void addAllListedCompaniesInCache(){
        List<ListedCompany> allListedCompanies = super.all();

        this.allListedCompaniesCache = allListedCompanies;

        for (var listedCompany : allListedCompanies) {
            this.indexedCacheByTicker.put(listedCompany.ticker(), listedCompany);
        }
    }
}
