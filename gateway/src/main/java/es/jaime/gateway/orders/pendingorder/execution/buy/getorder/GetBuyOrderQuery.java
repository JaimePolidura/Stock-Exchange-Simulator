package es.jaime.gateway.orders.pendingorder.execution.buy.getorder;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.authentication._shared.domain.UserName;
import es.jaime.gateway.orders._shared.domain.OrderId;
import lombok.Getter;

public final class GetBuyOrderQuery implements Query {
    @Getter private final OrderId orderId;
    @Getter private final UserName userName;

    public GetBuyOrderQuery(String orderId, String ussername) {
        this.orderId = OrderId.of(orderId);
        this.userName = UserName.of(ussername);
    }
}
