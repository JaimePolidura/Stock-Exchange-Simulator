package es.jaime.gateway.orders.pendingorder._shared.domain;

import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrder;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.ExecutionOrderFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public final class PendingOrderFinder {
    private final ExecutionOrderFinder executionOrderFinder;
    private final OrdersCancelRepository cancelOrdersRepository;

    public List<PendingOrder> findPendingOrdersFor(ListedCompany listedCompany){
        List<PendingOrder> toReturn = new ArrayList<>();

        List<ExecutionOrder> executionOrders = executionOrderFinder.findByTicker(OrderTicker.of(listedCompany.ticker().value()));
        List<CancelOrder> cancelOrders = cancelOrdersRepository.findByTicker(OrderTicker.of(listedCompany.ticker().value()));

        toReturn.addAll(executionOrders);
        toReturn.addAll(cancelOrders);

        return toReturn.stream()
                .filter(PendingOrder::isPending)
                .collect(Collectors.toList());
    }
}
