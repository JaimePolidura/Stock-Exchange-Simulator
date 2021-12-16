package es.jaime.exchange.infrastructure;

import es.jaime.exchange.domain.EventBus;
import es.jaime.exchange.domain.ExchangeConfiguration;
import es.jaime.exchange.domain.Order;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration implements MessageListener {
    private final ExchangeConfiguration exchangeConfiguration;
    private final EventBus eventBus;

    public RabbitMQConfiguration(ExchangeConfiguration configuration, EventBus eventBus) {
        this.exchangeConfiguration = configuration;
        this.eventBus = eventBus;
    }

    @Bean
    public CachingConnectionFactory connection() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setHost("localhost");
        factory.setPort(5672);

        return factory;
    }

    @Override
    public void onMessage(Message message) {
        Order order = Order.create(message.getBody());

        eventBus.publish(new OrderArrivedEvent(order));
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();

        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(executedOrdersQueue(), errorOrdersQueue());
        simpleMessageListenerContainer.setMessageListener(this);

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
