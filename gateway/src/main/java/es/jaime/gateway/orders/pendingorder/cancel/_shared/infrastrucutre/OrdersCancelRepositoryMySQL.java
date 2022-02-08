package es.jaime.gateway.orders.pendingorder.cancel._shared.infrastrucutre;

import es.jaime.configuration.DatabaseConfiguration;
import es.jaime.gateway.orders._shared.domain.*;
import es.jaime.gateway.orders.pendingorder._shared.domain.PendingOrderType;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.CancelOrder;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrderIdToCancel;
import es.jaime.gateway.orders.pendingorder.cancel._shared.domain.OrdersCancelRepository;
import es.jaime.mapper.EntityMapper;
import es.jaime.repository.DataBaseRepositoryValueObjects;
import es.jaimetruman.select.Select;
import es.jaimetruman.select.SelectOptionCompare;
import es.jaimetruman.select.SelectOptionFull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class OrdersCancelRepositoryMySQL extends DataBaseRepositoryValueObjects<CancelOrder, OrderId> implements OrdersCancelRepository {
    protected OrdersCancelRepositoryMySQL(DatabaseConfiguration databaseConfiguration) {
        super(databaseConfiguration);
    }

    @Override
    public void save(CancelOrder orderCancel) {
        super.save(orderCancel);
    }

    @Override
    public Optional<CancelOrder> findById(OrderId orderId) {
        return super.findById(orderId);
    }

    @Override
    public List<CancelOrder> findByClientIdAndOrderState(OrderClientId clientId, OrderState orderState) {
        return buildListFromQuery(
                Select.from("cancel_orders").where("clientId").equal(clientId.value()).and("state").equal(orderState.value())
        );
    }

    @Override
    public List<CancelOrder> findByTicker(OrderTicker orderTicker) {
        return buildListFromQuery(
                Select.from("cancel_orders").where("ticker").equal(orderTicker.value())
        );
    }

    @Override
    public List<CancelOrder> findByOrdersId(List<OrderId> ordersId) {
        SelectOptionFull selectQuery = Select.from("cancel_orders")
                .where("orderId").equal(ordersId.get(0).value());

        if(ordersId.size() == 1) return buildListFromQuery(selectQuery);

        for (int i = 1; i < ordersId.size(); i++) {
            String orderId = ordersId.get(i).value();

            selectQuery = selectQuery.or("orderId").equal(orderId);
        }

        return buildListFromQuery(selectQuery);
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
    protected Map<String, Object> toPrimitives(CancelOrder cancelOrder) {
        return Map.of(
                "orderId", cancelOrder.getOrderId().value(),
                "clientId", cancelOrder.getClientId().value(),
                "date", cancelOrder.getDate().value(),
                "state", cancelOrder.getState().value(),
                "ticker", cancelOrder.getTicker().value(),
                "pendingOrderType", cancelOrder.getPendingOrderType().value(),
                "orderIdToCancel", cancelOrder.getOrderIdToCancel().value()
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

    @Override
    protected Function<OrderId, Object> idValueObjectToIdPrimitive() {
        return OrderId::value;
    }
}
