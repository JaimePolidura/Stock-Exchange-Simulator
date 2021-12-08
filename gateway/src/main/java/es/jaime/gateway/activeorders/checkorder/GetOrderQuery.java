package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderID;
import es.jaime.gateway.authentication._shared.domain.UserName;
import lombok.Getter;


public final class GetOrderQuery implements Query {
    @Getter private final ActiveOrderID activeOrderID;
    @Getter private final UserName userName;

    public GetOrderQuery(String activeOrderID, String userName) {
        this.activeOrderID = ActiveOrderID.of(activeOrderID);
        this.userName = UserName.of(userName);
    }
}
