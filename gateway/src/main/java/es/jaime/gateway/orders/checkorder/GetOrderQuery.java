package es.jaime.gateway.orders.checkorder;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.authentication._shared.domain.UserName;
import lombok.Getter;


public final class GetOrderQuery implements Query {
    @Getter private final OrderID orderId;
    @Getter private final UserName userName;

    public GetOrderQuery(String orderIdStrign, String userName) {
        this.orderId = OrderID.of(orderIdStrign);
        this.userName = UserName.of(userName);
    }
}
