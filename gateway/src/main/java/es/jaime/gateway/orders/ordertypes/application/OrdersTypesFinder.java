package es.jaime.gateway.orders.ordertypes.application;

import es.jaime.gateway.orders.ordertypes.domain.OrderType;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeId;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeName;
import es.jaime.gateway.orders.ordertypes.domain.OrderTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public final class OrdersTypesFinder {
    private final OrderTypeRepository orderTypes;

    public OrdersTypesFinder(OrderTypeRepository orderTypes) {
        this.orderTypes = orderTypes;
    }

    public boolean existsTypes(OrderTypeId ...orderTypeId){
        return Arrays.stream(orderTypeId).allMatch(this::existsType);
    }
    
    public boolean existsType(OrderTypeId orderTypeId){
        return this.orderTypes.findByOrderTypeId(orderTypeId).isPresent();
    }

    public boolean isOrderType(OrderTypeId actualOrderTypeId, OrderTypeName expectedName){
        Optional<OrderType> actualOrderType = this.orderTypes.findByOrderTypeId(actualOrderTypeId);

        return actualOrderType.isPresent() && actualOrderType.get()
                .getName()
                .value().equalsIgnoreCase(expectedName.value());
    }
}
