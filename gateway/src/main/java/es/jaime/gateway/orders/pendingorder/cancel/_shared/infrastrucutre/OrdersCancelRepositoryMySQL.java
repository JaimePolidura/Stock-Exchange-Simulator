package es.jaime.gateway.orders.pendingorder.cancel._shared.infrastrucutre;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder._shared.infrastructure.PendingOrderRepositoryMySQL;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderIdToCancel;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.mapper.EntityMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class OrdersCancelRepositoryMySQL extends PendingOrderRepositoryMySQL<CancelOrder> implements OrdersCancelRepository {
    protected OrdersCancelRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    protected EntityMapper<CancelOrder> entityMapper() {
        return EntityMapper
                .table("cancel_orders")
                .classToMap(CancelOrder.class)
                .idField("orderId")
                .build();
    }

    @Override
    public CancelOrder buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new CancelOrder(
                OrderId.of(resultSet.getString("orderId")),
                OrderClientId.of(resultSet.getString("clientId")),
                OrderDate.of(resultSet.getString("date")),
                OrderState.of(resultSet.getString("state")),
                OrderTicker.of(resultSet.getString("ticker")),
                PendingOrderType.of(resultSet.getString("pendingOrderType")),
                OrderIdToCancel.of("orderIdToCancel")
        );
    }

    @Override
    protected Map<String, Object> toValueObjects(CancelOrder cancelOrder) {
        return Map.of(
                "orderId", cancelOrder.getOrderId(),
                "clientId", cancelOrder.getClientId(),
                "date", cancelOrder.getDate(),
                "state", cancelOrder.getState(),
                "ticker", cancelOrder.getTicker(),
                "pendingOrderType", cancelOrder.getPendingOrderType(),
                "orderIdToCancel", cancelOrder.getOrderIdToCancel()
        );
    }
}
