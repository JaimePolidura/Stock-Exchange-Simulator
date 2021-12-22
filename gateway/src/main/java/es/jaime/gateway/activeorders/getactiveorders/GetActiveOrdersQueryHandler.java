package es.jaime.gateway.activeorders.getactiveorders;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderRepository;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies.getlistedcomapny.ListedCompanyFinderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetActiveOrdersQueryHandler implements QueryHandler<GetActiveOrdersQuery, GetActiveOrdersQueryResponse> {
    private final ActiveOrderRepository activeOrders;
    private final ListedCompanyFinderService listedCompanies;

    public GetActiveOrdersQueryHandler(ActiveOrderRepository repository,
                                       ListedCompanyFinderService listedCompanyFinder) {
        this.activeOrders = repository;
        this.listedCompanies = listedCompanyFinder;
    }

    @Override
    public GetActiveOrdersQueryResponse handle(GetActiveOrdersQuery query) {
        List<ActiveOrder> activeOrders = this.activeOrders.findByClientId(query.getClientID())
                .orElseThrow(() -> new ResourceNotFound("No orders found for you"));
        List<ListedCompany> listedCompanies = this.listedCompanies.all();

        return GetActiveOrdersQueryResponse.fromAggregate(activeOrders, listedCompanies);
    }
}
