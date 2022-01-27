package es.jaime.gateway._shared.infrastrocture.bus.command;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway._shared.domain.command.CommandBus;
import es.jaime.gateway._shared.domain.database.TransactionManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class InMemoryCommandBus implements CommandBus {
    private final CommandHandlerMapper handlerMapper;
    private final TransactionManager transactionManager;

    @Override
    public void dispatch(Command command) {
        try {
            transactionManager.start();
            handlerMapper.search(command.getClass()).handle(command);
            transactionManager.commit();
        }catch (Exception ignored){
            transactionManager.rollback();
        }
    }
}
