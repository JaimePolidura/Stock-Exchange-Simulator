package es.jaime.gateway.orders.getorders;

import es.jaime.gateway._shared.domain.exceptions.ResourceNotFound;
import es.jaime.gateway._shared.domain.query.QueryResponse;
import es.jaime.gateway.orders._shared.domain.Order;
import es.jaime.gateway.orders._shared.domain.OrderTicker;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetOrdersQueryResponse implements QueryResponse {
    @Getter private final List<OrderDataResponse> orders;

    public static GetOrdersQueryResponse fromAggregate(List<Order> orders, List<ListedCompany> listedCompanies){
        return new GetOrdersQueryResponse(orders.stream()
                .map(order -> OrderDataResponse.create(order, findListedCompanyFor(order.ticker(), listedCompanies)))
                .collect(Collectors.toList()));
    }

    private static ListedCompany findListedCompanyFor(OrderTicker ticker, List<ListedCompany> listedCompanies){
        return listedCompanies.stream()
                .filter(listedCompany -> sameTicker(ticker, listedCompany))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Ticker not found"));
    }

    private static boolean sameTicker(OrderTicker ticker, ListedCompany listedCompany){
        return ticker.value().equalsIgnoreCase(listedCompany.ticker().value());
    }

    @AllArgsConstructor
    public static final class OrderDataResponse {
        @Getter private final String orderId;
        @Getter private final String clientId;
        @Getter private final String ticker;
        @Getter private final String date;
        @Getter private final int quantity;
        @Getter private final String type;
        @Getter private final double executionPrice;
        @Getter private final String status;
        @Getter private final String name;
        @Getter private final Currency currency;

        public static OrderDataResponse create(Order order, ListedCompany listedCompany){
            return new OrderDataResponse(
                    order.orderId().value(),
                    order.clientId().value(),
                    order.ticker().value(),
                    order.date().value(),
                    order.quantity().value(),
                    order.type().valueString(),
                    order.executionPrice().value(),
                    order.status().valueString(),
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
