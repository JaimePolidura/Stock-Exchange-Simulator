package es.jaime.exchange.application;

import es.jaime.exchange.domain.MatchingEngine;
import es.jaime.exchange.domain.Order;
import es.jaime.exchange.domain.OrderType;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class OnOrderReceivedListener implements MessageListener {
    private final MatchingEngine matchingEngine;

    public OnOrderReceivedListener(MatchingEngine matchingEngine) {
        this.matchingEngine = matchingEngine;
    }

    @Override
    public void onMessage(Message message) {
        byte[] rawBody = message.getBody();

        Order order = processAndSend(rawBody);

        this.matchingEngine.enqueue(order);
    }

    //TODO we should send data back to the client to tell them error
    @SneakyThrows
    private Order processAndSend(byte[] rawBody){
        String rawBodyString = new String(rawBody, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(rawBodyString);

        return new Order(
                String.valueOf(jsonObject.get("orderId")),
                String.valueOf(jsonObject.get("clientId")),
                String.valueOf(jsonObject.get("date")),
                Double.parseDouble(String.valueOf(jsonObject.get("executionPrice"))),
                Integer.parseInt(String.valueOf(jsonObject.get("quantity"))),
                String.valueOf(jsonObject.get("ticker")),
                OrderType.valueOf(String.valueOf(jsonObject.get("type")))
        );
    }
}
