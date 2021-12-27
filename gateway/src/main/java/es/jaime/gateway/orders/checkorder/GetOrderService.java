package es.jaime.gateway.orders.checkorder;

import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetOrderService {
    private final OrdersRepository repository;

    public GetOrderService(OrdersRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> get (OrderID activeOrderID) {
        return repository.findByOrderId(activeOrderID);
    }
}
