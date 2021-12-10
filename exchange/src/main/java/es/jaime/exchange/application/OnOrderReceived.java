package es.jaime.exchange.application;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class OnOrderReceived implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("Hola");
    }
}
