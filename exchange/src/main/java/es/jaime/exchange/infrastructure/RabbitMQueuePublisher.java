package es.jaime.exchange.infrastructure;

import es.jaime.exchange.ExchangeConfiguration;
import es.jaime.exchange.domain.QueueMessage;
import es.jaime.exchange.domain.QueuePublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQueuePublisher implements QueuePublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ExchangeConfiguration exchangeConfiguration;

    public RabbitMQueuePublisher(RabbitTemplate rabbitTemplate, ExchangeConfiguration exchangeConfiguration) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeConfiguration = exchangeConfiguration;
    }

    @Override
    public void enqueue(QueueMessage queueMessage) {
        this.rabbitTemplate.convertAndSend(
                exchangeConfiguration.getQueueExchangeName(),
                exchangeConfiguration.getQueueExecutedOrdersName(),
                queueMessage.toJson()
        );
    }
}
