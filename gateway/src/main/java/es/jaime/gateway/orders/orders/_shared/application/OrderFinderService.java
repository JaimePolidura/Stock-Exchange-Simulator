package es.jaime.gateway.orders.orders._shared.application;

import es.jaime.gateway.orders.orders._shared.domain.Order;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import es.jaime.gateway.orders.orders._shared.domain.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderFinderService {
    private final OrdersRepository repository;

    public OrderFinderService(OrdersRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> findByOrderId(OrderId activeOrderID) {
        return repository.findByOrderId(activeOrderID);
    }
}
