package es.jaime.gateway.orders._shared.infrastrocture;

import es.jaime.gateway.orders._shared.domain.ActiveOrder;
import es.jaime.gateway.orders._shared.domain.ActiveOrderID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveOrderRepositoryJPA extends CrudRepository<ActiveOrder, ActiveOrderID> {
    @Override <S extends ActiveOrder> S save(S entity);
}
