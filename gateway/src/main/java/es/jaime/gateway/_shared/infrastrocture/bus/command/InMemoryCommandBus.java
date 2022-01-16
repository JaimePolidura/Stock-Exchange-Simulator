package es.jaime.gateway._shared.infrastrocture.bus.command;

import es.jaime.gateway._shared.domain.command.Command;
import es.jaime.gateway._shared.domain.command.CommandBus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InMemoryCommandBus implements CommandBus {
    private final CommandHandlerMapper handlerMapper;

    public InMemoryCommandBus(CommandHandlerMapper handlerMapper) {
        this.handlerMapper = handlerMapper;
    }

    @Override
    @Transactional
    public void dispatch(Command command) {
        handlerMapper.search(command.getClass()).handle(command);
    }
}
