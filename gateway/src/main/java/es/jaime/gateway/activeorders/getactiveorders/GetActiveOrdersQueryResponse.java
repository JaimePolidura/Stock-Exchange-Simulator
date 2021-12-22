package es.jaime.gateway.activeorders.getactiveorders;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrder;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderTicker;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetActiveOrdersQueryResponse implements QueryResponse {
    @Getter private final List<ActiveOrderDataResponse> activeOrders;

    public static GetActiveOrdersQueryResponse fromAggregate(List<ActiveOrder> activeOrders, List<ListedCompany> listedCompanies){
        return new GetActiveOrdersQueryResponse(activeOrders.stream()
                .map(order -> ActiveOrderDataResponse.create(order, findListedCompanyFor(order.ticker(), listedCompanies)))
                .collect(Collectors.toList()));
    }

    private static ListedCompany findListedCompanyFor(ActiveOrderTicker ticker, List<ListedCompany> listedCompanies){
        return listedCompanies.stream()
                .filter(listedCompany -> sameTicker(ticker, listedCompany))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Ticker not found"));
    }

    private static boolean sameTicker(ActiveOrderTicker ticker, ListedCompany listedCompany){
        return ticker.value().equalsIgnoreCase(listedCompany.ticker().value());
    }

    @AllArgsConstructor
    public static final class ActiveOrderDataResponse {
        @Getter private final String activeorderId;
        @Getter private final String clientId;
        @Getter private final String ticker;
        @Getter private final String date;
        @Getter private final int quantity;
        @Getter private final String type;
        @Getter private final double executionPrice;
        @Getter private final String status;
        @Getter private final String name;
        @Getter private final Currency currency;

        public static ActiveOrderDataResponse create(ActiveOrder activeOrder, ListedCompany listedCompany){
            return new ActiveOrderDataResponse(
                    activeOrder.activeorderId().value(),
                    activeOrder.clientId().value(),
                    activeOrder.ticker().value(),
                    activeOrder.date().value(),
                    activeOrder.quantity().value(),
                    activeOrder.type().valueString(),
                    activeOrder.executionPrice().value(),
                    activeOrder.status().valueString(),
                    listedCompany.name().value(),
                    new Currency(
                            listedCompany.currencySymbol().value(),
                            listedCompany.currencyCode().value()
                    )
            );
        }
    }

    @AllArgsConstructor
    private static class Currency {
        @Getter private final String symbol;
        @Getter private final String code;
    }
}
