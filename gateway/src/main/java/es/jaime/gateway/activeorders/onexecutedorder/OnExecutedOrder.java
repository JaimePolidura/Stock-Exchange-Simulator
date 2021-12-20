package es.jaime.gateway.activeorders.onexecutedorder;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OnExecutedOrder {
    @RabbitListener(queues = "sxs.executed-orders")
    public void on(String body){
        System.out.println(body);
    }
}
