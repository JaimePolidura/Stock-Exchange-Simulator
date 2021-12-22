package es.jaime.gateway._shared.infrastrocture.persistance;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Selection;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class HibernateRepository<T extends Aggregate> {
    protected final SessionFactory sessionFactory;
    protected final Class<T> aggregateClass;

    public HibernateRepository(SessionFactory sessionFactory, Class<T> aggregateClass) {
        this.sessionFactory    = sessionFactory;
        this.aggregateClass    = aggregateClass;
    }

    protected void persist(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }

    protected Optional<T> byId(Serializable id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().byId(aggregateClass).load(id));
    }

    protected List<T> all() {
        CriteriaQuery<T> criteria = sessionFactory.getCriteriaBuilder().createQuery(aggregateClass);

        criteria.from(aggregateClass);

        return sessionFactory.getCurrentSession().createQuery(criteria).getResultList();
    }

    @SneakyThrows
    protected Optional<List<T>> byQuery(String query){
        Session session = sessionFactory.getCurrentSession();
        List<Object[]> result = session.createSQLQuery(query).getResultList();

        if(result == null || result.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(result.stream()
                .map(this::map)
                .collect(Collectors.toList()));
    }

    private T map (Object[] values){
        return queryMapper().apply(values);
    }

    /**
     * Override this if you want to implement querys
     */
    protected Function<Object[], T> queryMapper(){
        return null;
    }

}
