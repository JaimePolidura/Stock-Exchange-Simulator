package es.jaimetruman.exchanges.exchange;

import es.jaimetruman._shared.Order;
import es.jaimetruman.exchangedistribuitor._shared.LimitOrderExecutionType;
import es.jaimetruman.exchangedistribuitor._shared.MarketOrderExecutionType;
import es.jaimetruman.exchangedistribuitor._shared.OrderType;
import es.jaimetruman.exchanges.orderexecutor.OrderExecutor;
import es.jaimetruman.exchanges.orderexecutor.OrderExecutorImpl;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public final class Exchange implements Runnable{
    @Getter private final String ticker;
    @Getter private final Queue<OrderAndAttempts> buyers;
    @Getter private final Queue<OrderAndAttempts> sellers;
    private final OrderExecutor orderExecutor;
    private boolean checkSellToBuyersTurn;

    public Exchange(String ticker){
        this.ticker = ticker;
        this.buyers = new LinkedList<>();
        this.sellers = new LinkedList<>();
        this.orderExecutor = new OrderExecutorImpl();
    }

    public void enqueueOrder(Order order){
        if(order.getOrderType() == OrderType.BUY){
            this.buyers.add(new OrderAndAttempts(order));
        }else{
            this.sellers.add(new OrderAndAttempts(order));
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            OrderAndAttempts lastOrderA = checkSellToBuyersTurn ? sellers.poll() : buyers.poll();
            Iterator<OrderAndAttempts> lastOrdersBIterator = checkSellToBuyersTurn ? sellers.iterator() : buyers.iterator();

            if(lastOrderA == null || !lastOrdersBIterator.hasNext()){
                 Thread.sleep(100);
                 continue;
            }

            boolean orderExecuted = false;
            while(lastOrdersBIterator.hasNext()){
                OrderAndAttempts lastOrderB = lastOrdersBIterator.next();

                orderExecuted = checkOrderAndExecuteIfPossible(lastOrderA, lastOrderB);

                if(orderExecuted){
                    lastOrdersBIterator.remove();
                    break;
                }
            }

            if(!orderExecuted){
                lastOrderA.incrementAttempts();

                //Reenqueue the order if not found
                if(checkSellToBuyersTurn)
                    this.sellers.add(lastOrderA);
                else
                    this.buyers.add(lastOrderA);
            }

            this.checkSellToBuyersTurn = !checkSellToBuyersTurn;
            Thread.sleep(100);
        }
    }

    private boolean checkOrderAndExecuteIfPossible(OrderAndAttempts orderA, OrderAndAttempts orderB){
        if(isOrderExecutionTypeMarket(orderA) || isOrderExecutionTypeMarket(orderB)){
            orderExecutor.execute(orderA.order, orderB.order);
            return true;

        }else if(isPriceMatchedExecutionTypePrice(orderA, orderB)){
            orderExecutor.execute(orderA.order, orderB.order);

            return true;
        }

        return false;
    }

    private boolean isPriceMatchedExecutionTypePrice(OrderAndAttempts order1, OrderAndAttempts order2){
        return getPriceFromExecutionTypeLimit(order1) == getPriceFromExecutionTypeLimit(order2);
    }

    private double getPriceFromExecutionTypeLimit(OrderAndAttempts order){
        return ((LimitOrderExecutionType) order.order.getOrderExecutionType()).getPrice();
    }

    private boolean isOrderExecutionTypeMarket(OrderAndAttempts order){
        return order.order.getOrderExecutionType() instanceof MarketOrderExecutionType;
    }

    private static class OrderAndAttempts {
        final Order order;
        int attempts;

        public OrderAndAttempts(Order order) {
            this.order = order;
            this.attempts = 10;
        }

        public void incrementAttempts(){
            ++attempts;
        }
    }
}
