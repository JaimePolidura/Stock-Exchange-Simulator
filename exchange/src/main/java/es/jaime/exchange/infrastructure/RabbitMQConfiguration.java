package es.jaime.exchange.infrastructure;

import es.jaime.exchange.ExchangeConfiguration;
import es.jaime.exchange.application.OnOrderReceivedListener;
import es.jaime.exchange.domain.MatchingEngine;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
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
        simpleMessageListenerContainer.setQueues(executedOrdersQueue(), errorOrdersQueue());
        simpleMessageListenerContainer.setMessageListener(new OnOrderReceivedListener(matchingEngine));

        return simpleMessageListenerContainer;
    }

    @Bean("executedorders-binding")
    public Binding bindingExecutedOrders(@Qualifier("executedorders-queue") Queue queue,
                                         @Qualifier("executedorders-exchange") TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(exchangeConfiguration.executedOrdersExchangeName());
    }

    @Bean("executedorders-exchange")
    public TopicExchange executedOrdersExchange(){
        return new TopicExchange(exchangeConfiguration.executedOrdersExchangeName());
    }

    @Bean("executedorders-queue")
    public Queue executedOrdersQueue() {
        return new Queue(exchangeConfiguration.executedOrdersQueueName(), false);
    }

    @Bean("errororders-binding")
    public Binding bindingErrorOrders(@Qualifier("executedorders-queue") Queue queue,
                                      @Qualifier("errororders-exchange") TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(exchangeConfiguration.errorOrdersExchangeName());
    }

    @Bean("errororders-exchange")
    public TopicExchange errorOrdersExchange(){
        return new TopicExchange(exchangeConfiguration.errorOrdersExchangeName());
    }

    @Bean("errororders-queue")
    public Queue errorOrdersQueue(){
        return new Queue(exchangeConfiguration.errorOrdersQueueName(), false);
    }
}
