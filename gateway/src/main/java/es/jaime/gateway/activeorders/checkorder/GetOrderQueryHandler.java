package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.query.QueryHandler;
import es.jaime.gateway._shared.domain.exceptions.IllegalAccess;
import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyTicker;
import es.jaime.gateway.listedcompanies.getlistedcomapny.ListedCompanyFinderService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetOrderQueryHandler implements QueryHandler<GetOrderQuery, GetOrderQueryResponse> {
    private final GetOrderService getOrderService;
    private final ListedCompanyFinderService listedCompanyFinderService;

    public GetOrderQueryHandler(GetOrderService getOrderService, ListedCompanyFinderService listedCompanyFinderService) {
        this.getOrderService = getOrderService;
        this.listedCompanyFinderService = listedCompanyFinderService;
    }

    @Override
    public GetOrderQueryResponse handle(GetOrderQuery query) {
        ActiveOrder activeOrder = getOrderService.get(query.getActiveOrderID())
                .orElseThrow(() -> new ResourceNotFound("Active order for that ID wasn't found!"));

        ensureUserOwnsTheOrder(activeOrder, query.getUserName());

        var listedCompanyData = listedCompanyFinderService.find(ListedCompanyTicker.of(activeOrder.ticker().value()));

        return new GetOrderQueryResponse(
                activeOrder.activeorderId().value(),
                activeOrder.ticker().value(),
                activeOrder.type().valueString(),
                activeOrder.executionPrice().value(),
                activeOrder.quantity().value(),
                activeOrder.date().value(),
                activeOrder.status().valueString(),
                listedCompanyData.currencyCode().value(),
                listedCompanyData.currencySymbol().value()
        );
    }

    private void ensureUserOwnsTheOrder(ActiveOrder activeOrder, UserName userName){
        if(!userName.value().equals(activeOrder.clientId().value())){
            throw new IllegalAccess("You dont own that order!");
        }
    }
}
