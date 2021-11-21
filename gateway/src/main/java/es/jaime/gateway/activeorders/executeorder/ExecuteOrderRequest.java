package es.jaime.gateway.activeorders.executeorder;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public final class ExecuteOrderRequest{
    public final String clientId;
    public final String ticker;
    public final int quantity;
    public final String orderType;
    public final double executionType;
}
