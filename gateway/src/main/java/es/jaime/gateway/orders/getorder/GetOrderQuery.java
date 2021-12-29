package es.jaime.gateway.orders.getorder;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.authentication._shared.domain.UserName;
import lombok.Getter;

public final class GetOrderQuery implements Query {
    @Getter private final OrderID orderId;
    @Getter private final UserName userName;

    public GetOrderQuery(String orderId, String ussername) {
        this.orderId = OrderID.of(orderId);
        this.userName = UserName.of(ussername);
    }
}
