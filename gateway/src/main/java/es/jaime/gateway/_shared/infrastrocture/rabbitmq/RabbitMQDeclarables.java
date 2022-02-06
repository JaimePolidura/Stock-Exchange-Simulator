package es.jaime.gateway._shared.infrastrocture.rabbitmq;

import es.jaime.gateway._shared.domain.EventName;
import es.jaime.gateway._shared.domain.Utils;
import es.jaime.gateway._shared.infrastrocture.exchanges.ExchangesStarter;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompany;
import es.jaime.gateway.listedcompanies._shared.domain.ListedCompanyFinderService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;

import static es.jaime.gateway._shared.infrastrocture.rabbitmq.RabbitMQNameFormatter.START_EXCHANGE;
import static java.lang.String.*;

@Configuration("rabbitmq-declarables")
@DependsOn({"rabbitmq-configuration"})
@AllArgsConstructor
public class RabbitMQDeclarables {
    private final ListedCompanyFinderService listedCompanyFinder;

    @Bean
    public Declarables declarables() {
        List<Declarable> start = start();
        List<Declarable> events = events();

        return new Declarables(Utils.toList(start, events));
    }

    private List<Declarable> events(){
        List<EventName> events = EventName.toListEvents();
        TopicExchange exchangeEvents = new TopicExchange("sxs.events");
        List<Declarable> declarables = new ArrayList<>();

        for (EventName event : events) {
            List<String> listeners = event.getListeners();

            for (String listener : listeners) {
                //TODO Improve
                if(listener.startsWith("exchange")) continue;

                Queue queue = new Queue(format("sxs.events.%s.%s", event.getName(), listener));
                String routingKey = format("sxs.events.%s.*", event.getName());

                Binding binding = BindingBuilder.bind(queue)
                        .to(exchangeEvents)
                        .with(routingKey);

                declarables.add(binding);
                declarables.add(queue);
            }
        }

        declarables.addAll(eventNewOrders(exchangeEvents));

        return declarables;
    }

    private List<Declarable> eventNewOrders(TopicExchange eventsExchange){
        List<ListedCompany> listedCompanies = this.listedCompanyFinder.all();
        List<Declarable> toReturn = new ArrayList<>();
        String eventName = EventName.ORDER_PUBLISHED.getName();

        for (ListedCompany listedCompany : listedCompanies) {
            String ticker = listedCompany.ticker().value();
            String listenerName = ExchangesStarter.nameForExchangeContainer(ticker);

            Queue queue = new Queue(format("sxs.events.%s.%s", eventName, listenerName));
            Binding binding = BindingBuilder.bind(queue)
                    .to(eventsExchange)
                    .with(format("sxs.events.%s.%s", eventName, listenerName));

            toReturn.add(queue);
            toReturn.add(binding);
        }

        return toReturn;
    }

    private List<Declarable> start(){
        TopicExchange startTopicExchange = new TopicExchange(START_EXCHANGE);
        Queue startQueue = new Queue(START_EXCHANGE, false);
        Binding binding = BindingBuilder.bind(startQueue).to(startTopicExchange).with(START_EXCHANGE);

        return List.of(startTopicExchange, startQueue, binding);
    }
}
