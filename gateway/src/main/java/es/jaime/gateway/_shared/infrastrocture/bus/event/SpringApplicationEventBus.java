package es.jaime.gateway._shared.infrastrocture.bus.event;

import es.jaime.gateway._shared.domain.database.TransactionManager;
import es.jaime.gateway._shared.domain.event.DomainEvent;
import es.jaime.gateway._shared.domain.event.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class SpringApplicationEventBus implements EventBus {
    private final ApplicationEventPublisher publisher;
    private final TransactionManager transactionManager;

    @Override
    public void publish(final List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    @Override
    public void publish(final DomainEvent event) {
        try{
            transactionManager.start();
            this.publisher.publishEvent(event);
            transactionManager.commit();
        }catch (Exception e){
            transactionManager.rollback();
        }
    }
}
