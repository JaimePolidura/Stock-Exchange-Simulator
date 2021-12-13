package es.jaime.exchange.infrastructure;

import es.jaime.exchange.ExchangeConfiguration;
import es.jaime.exchange.application.OnOrderReceivedListener;
import es.jaime.exchange.domain.MatchingEngine;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    private final ExchangeConfiguration exchangeConfiguration;
    private final MatchingEngine matchingEngine;

    public RabbitMQConfiguration(ExchangeConfiguration configuration, MatchingEngine matchingEngine) {
        this.exchangeConfiguration = configuration;
        this.matchingEngine = matchingEngine;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue());
        simpleMessageListenerContainer.setMessageListener(new OnOrderReceivedListener(matchingEngine));

        return simpleMessageListenerContainer;
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchangeConfiguration.getQueueExchangeName());
    }

    @Bean
    public Queue queue() {
        return new Queue(exchangeConfiguration.getQueueExecutedOrdersName(), false);
    }
}
