package es.jaime.gateway.orders._shared.infrastrocture;

import es.jaime.gateway.orders._shared.domain.ActiveOrder;
import es.jaime.gateway.orders._shared.domain.ActiveOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActiveOrderRepositoryJPA extends JpaRepository<ActiveOrder, UUID>, ActiveOrderRepository {
}
