package es.jaime.gateway._shared.infrastrocture.persistance;

import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class HibernateRepository<T> {
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

    protected Optional<List<T>> byQuery(String query){
        return Optional.ofNullable(sessionFactory.getCurrentSession()
                .createQuery(query, aggregateClass)
                .getResultList());
    }
}
