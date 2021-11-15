package es.jaime.gateway._shared.infrastrocture.bus.command;

import es.jaime.gateway._shared.domain.bus.command.Command;
import es.jaime.gateway._shared.domain.bus.command.CommandHandler;
import es.jaime.gateway._shared.infrastrocture.bus.ApplicationCQRSClassMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public final class CommandHandlerMapper {
    private final Map<Class<Command>, CommandHandler> commandsHandlers;
    private final ApplicationContext context;
    private final ApplicationCQRSClassMapper mapper;

    public CommandHandlerMapper(ApplicationContext context, ApplicationCQRSClassMapper mapper) {
        this.context = context;
        this.mapper = mapper;

        this.commandsHandlers = mapper.find(CommandHandler.class);
    }

    public CommandHandler search(Class<? extends Command> commandClass) {
        return commandsHandlers.get(commandClass);
    }
}
