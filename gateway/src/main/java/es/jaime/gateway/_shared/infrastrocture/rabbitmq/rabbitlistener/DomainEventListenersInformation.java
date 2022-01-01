package es.jaime.gateway._shared.infrastrocture.rabbitmq.rabbitlistener;

import es.jaime.gateway._shared.domain.event.DomainEvent;
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
public class DomainEventListenersInformation {
    private final Map<String, DomainEvent> domainEventNamesWithInstances;

    public DomainEventListenersInformation() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner())
                .setUrls(ClasspathHelper.forPackage("es.jaime.gateway")));

        this.domainEventNamesWithInstances = mapDomainEventNamesWithInstances(reflections);
    }

    @SneakyThrows
    private Map<String, DomainEvent> mapDomainEventNamesWithInstances(Reflections reflections){
        Set<Method> methodsListeners = reflections.getMethodsAnnotatedWith(EventListener.class);
        Map<Class<? extends DomainEvent>, DomainEvent> domainEventsInstances = new HashMap<>();
        Map<String, DomainEvent> domainEventsNamesMappedWithInstances = new HashMap<>();

        for (Method methodsListener : methodsListeners) {
            Class<? extends DomainEvent> domainEventClass = (Class<? extends DomainEvent>) methodsListener.getParameterTypes()[0];

            System.out.println("0 " + domainEventClass.getName());

            if(domainEventsInstances.containsKey(domainEventClass)){
                continue;
            }
            if(Modifier.isAbstract(domainEventClass.getModifiers())){
                continue;
            }

            System.out.println("1 " + domainEventClass.getName());
            DomainEvent domainEventInstance = domainEventClass.newInstance();

            System.out.println("2 " + domainEventInstance.eventName());

            String eventName = domainEventInstance.eventName();
            if(eventName.equalsIgnoreCase("")){
                System.out.println("3");
                continue;
            }

            domainEventsInstances.put(domainEventClass, domainEventInstance);
            domainEventsNamesMappedWithInstances.put(eventName, domainEventInstance);

            System.out.println("4 " + eventName + " " + domainEventInstance);
        }

        return domainEventsNamesMappedWithInstances;
    }

    public DomainEvent getInstanceFor(String domainEventName){
        System.out.println("5 " + domainEventName);

        return this.domainEventNamesWithInstances.get(domainEventName);
    }
}
