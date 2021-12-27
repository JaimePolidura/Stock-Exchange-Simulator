package es.jaime.gateway.orders.getorders;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrdersRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersQueryHandler implements QueryHandler<GetOrdersQuery, GetOrdersQueryResponse> {
    private final OrdersRepository activeOrders;
    private final ListedCompanyFinderService listedCompanies;

    public GetOrdersQueryHandler(OrdersRepository repository,
                                 ListedCompanyFinderService listedCompanyFinder) {
        this.activeOrders = repository;
        this.listedCompanies = listedCompanyFinder;
    }

    @Override
    public GetOrdersQueryResponse handle(GetOrdersQuery query) {
        List<Order> activeOrders = this.activeOrders.findByClientId(query.getClientID())
                .orElseThrow(() -> new ResourceNotFound("No orders found for you"));
        List<ListedCompany> listedCompanies = this.listedCompanies.all();

        return GetOrdersQueryResponse.fromAggregate(activeOrders, listedCompanies);
    }
}
