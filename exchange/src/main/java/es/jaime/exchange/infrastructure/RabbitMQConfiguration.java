package es.jaime.exchange.infrastructure;

import es.jaime.exchange.ExchangeApplication;
import es.jaime.exchange.application.OnOrderReceived;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    public static final String executedOrdersTickerQueueName = "sxs.executed-orders." + ExchangeApplication.ticker;

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(new OnOrderReceived());
        return simpleMessageListenerContainer;

    }

    public Queue queue() {
        return new Queue(executedOrdersTickerQueueName, false);
    }
}
