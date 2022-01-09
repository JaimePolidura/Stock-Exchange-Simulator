package es.jaime.exchange.domain.models.orders;


public final class BuyOrder extends ExecutionOrder {
    public BuyOrder(String orderId, String clientId, String date, double executionPrice, int quantity, String ticker) {
        super(orderId, clientId, date, executionPrice, quantity, ticker);
    }

    @Override
    public int compareTo(ExecutionOrder other) {
        return (int) (other.getExecutionPrice() - getExecutionPrice());
    }
}
