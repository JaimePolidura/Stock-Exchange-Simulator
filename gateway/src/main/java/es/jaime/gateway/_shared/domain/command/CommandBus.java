package es.jaime.gateway._shared.domain.command;

public interface CommandBus {
    void dispatch(Command command);
}
