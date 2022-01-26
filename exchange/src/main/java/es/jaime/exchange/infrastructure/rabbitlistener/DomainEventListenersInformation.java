package es.jaime.exchange.infrastructure.rabbitlistener;

import es.jaime.exchange.domain.models.events.AsyncDomainEvent;
import es.jaime.exchange.domain.models.events.DomainEvent;
import es.jaime.exchange.domain.models.messages.MessageName;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component("eventslisteners-information")
public final class DomainEventListenersInformation {
    private final Map<String, AsyncDomainEvent> domainEventNamesWithInstances;

    public DomainEventListenersInformation() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner())
                .setUrls(ClasspathHelper.forPackage("es.jaime.exchange")));

        this.domainEventNamesWithInstances = mapDomainEventNamesWithInstances(reflections);
    }

    @SneakyThrows
    private Map<String, AsyncDomainEvent> mapDomainEventNamesWithInstances(Reflections reflections){
        Set<Method> methodsListeners = reflections.getMethodsAnnotatedWith(EventListener.class);

        Map<Class<? extends AsyncDomainEvent>, DomainEvent> domainEventsInstances = new HashMap<>();
        Map<String, AsyncDomainEvent> domainEventsNamesMappedWithInstances = new HashMap<>();

        for (Method methodsListener : methodsListeners) {
            Class<? extends DomainEvent> domainEventClass = (Class<? extends DomainEvent>) methodsListener.getParameterTypes()[0];

            if(domainEventsInstances.containsKey(domainEventClass)) continue;
            if(!isAsynchEvent(domainEventClass)) continue;
            if(Modifier.isAbstract(domainEventClass.getModifiers())) continue;

            Class<? extends AsyncDomainEvent> asynchDomainEventClass = (Class<? extends AsyncDomainEvent>) domainEventClass;

            AsyncDomainEvent domainEventInstance = asynchDomainEventClass.newInstance();
            MessageName eventName = domainEventInstance.eventName();

            if(eventName == null) continue;

            domainEventsInstances.put(asynchDomainEventClass, domainEventInstance);
            domainEventsNamesMappedWithInstances.put(eventName.getName(), domainEventInstance);
        }

        return domainEventsNamesMappedWithInstances;
    }

    private boolean isAsynchEvent(Class<? extends DomainEvent> eventClass){
        return AsyncDomainEvent.class.isAssignableFrom(eventClass);
    }

    public AsyncDomainEvent getInstanceFor(String domainEventName){
        return this.domainEventNamesWithInstances.get(domainEventName);
    }
}
