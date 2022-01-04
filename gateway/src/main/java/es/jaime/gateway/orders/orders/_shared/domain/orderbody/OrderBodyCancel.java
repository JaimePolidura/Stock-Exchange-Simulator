package es.jaime.gateway.orders.orders._shared.domain.orderbody;

import es.jaime.gateway.orders.orders._shared.domain.OrderBody;
import es.jaime.gateway.orders.orders._shared.domain.OrderId;
import lombok.Getter;
import org.json.JSONObject;

import java.util.Map;

public class OrderBodyCancel extends OrderBody {
    @Getter
    private final OrderId orderIdToCancel;

    public OrderBodyCancel(OrderId orderIdToCancel) {
        super(new JSONObject(Map.of(
                "orderIdToCancel", orderIdToCancel.value()
        )).toString());

        this.orderIdToCancel = orderIdToCancel;
    }

    public static OrderBodyCancel of(OrderId orderIdToCancel){
        return new OrderBodyCancel(orderIdToCancel);
    }
}
