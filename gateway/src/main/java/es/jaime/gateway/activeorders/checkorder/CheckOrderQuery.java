package es.jaime.gateway.activeorders.checkorder;

import es.jaime.gateway._shared.domain.bus.query.Query;
import es.jaime.gateway.activeorders._shared.domain.ActiveOrderID;
import es.jaime.gateway.authentication._shared.domain.UserName;
import lombok.Getter;

import java.util.UUID;

public final class CheckOrderQuery implements Query {
    @Getter private final ActiveOrderID activeOrderID;
    @Getter private final UserName userName;

    public CheckOrderQuery(String activeOrderID, String userName) {
        this.activeOrderID = ActiveOrderID.of(UUID.fromString(activeOrderID));
        this.userName = UserName.of(userName);
    }
}
