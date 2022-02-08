package es.jaime.gateway.orders.pendingorder.execution.buy._shared.infrastructure;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.execution._shared.domain.OrderPriceToExecute;
import es.jaime.gateway.orders.pendingorder.execution._shared.infrastructure.ExecutionOrderRepositoryMySQL;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrder;
import es.jaime.gateway.orders.pendingorder.execution.buy._shared.domain.BuyOrderRepostory;
import es.jaime.mapper.EntityMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class BuyOrderRepositoryMySQL extends ExecutionOrderRepositoryMySQL<BuyOrder> implements BuyOrderRepostory {
    protected BuyOrderRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    protected Map<String, Object> toValueObjects(BuyOrder buyOrder) {
        return Map.of(
                "orderId", buyOrder.getOrderId(),
                "clientId", buyOrder.getClientId(),
                "date", buyOrder.getDate(),
                "state", buyOrder.getState(),
                "ticker", buyOrder.getTicker(),
                "pendingOrderType", buyOrder.getPendingOrderType(),
                "quantity", buyOrder.getQuantity(),
                "priceToExecute", buyOrder.getPriceToExecute()
        );
    }

    @Override
    protected EntityMapper<BuyOrder> entityMapper() {
        return EntityMapper
                .table("buy_orders")
                .idField("orderId")
                .classToMap(BuyOrder.class)
                .build();
    }

    @Override
    public BuyOrder buildObjectFromResultSet(ResultSet resultSet) throws SQLException {
        return new BuyOrder(
                OrderId.of(resultSet.getString("orderId")),
                OrderClientId.of(resultSet.getString("clientId")),
                OrderDate.of(resultSet.getString("date")),
                OrderState.of(resultSet.getString("state")),
                OrderTicker.of(resultSet.getString("ticker")),
                PendingOrderType.of(resultSet.getString("pendingOrderType")),
                OrderQuantity.of(resultSet.getInt("quantity")),
                OrderPriceToExecute.of(resultSet.getDouble("priceToExecute"))
        );
    }
}
