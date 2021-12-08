package es.jaime.gateway._shared.domain.command;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
