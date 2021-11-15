package es.jaime.gateway._shared.infrastrocture.bus.command;

import es.jaime.gateway._shared.domain.bus.command.Command;
import es.jaime.gateway._shared.domain.bus.command.CommandBus;
import org.springframework.stereotype.Service;

@Service
public class InMemoryCommandBus implements CommandBus {
    private final CommandHandlerMapper handlerMapper;

    public InMemoryCommandBus(CommandHandlerMapper handlerMapper) {
        this.handlerMapper = handlerMapper;
    }

    @Override
    public void dispatch(Command command) {
        handlerMapper.search(command.getClass()).handle(command);
    }
}
