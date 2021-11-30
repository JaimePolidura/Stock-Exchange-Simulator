package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderID;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetOrderService {
    private final ActiveOrderRepository repository;

    public GetOrderService(ActiveOrderRepository repository) {
        this.repository = repository;
    }

    public Optional<ActiveOrder> get (ActiveOrderID activeOrderID) {
        return repository.findByOrderId(activeOrderID);
    }
}
