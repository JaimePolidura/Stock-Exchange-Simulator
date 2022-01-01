package es.jaime.gateway.orders._shared.infrastructure.rabbitmq;


import es.jaime.gateway._shared.domain.event.EventBus;
import es.jaime.gateway.orders._shared.domain.OrderID;
import es.jaime.gateway.orders._shared.domain.events.ErrorDuringOrderExecution;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

@Service
@DependsOn({"rabbitmq-starter"})
public class OnErrorOrderRabbitListener {
    private final EventBus eventBus;

    public OnErrorOrderRabbitListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @RabbitListener(queues = "sxs.exchange.events.gateway.order-error")
    public void on(String body){
        JSONObject jsonObject = new JSONObject(body);
        String orderId = jsonObject.getJSONObject("body").getString("orderId");

        this.eventBus.publish(new ErrorDuringOrderExecution(orderId));
    }
}
