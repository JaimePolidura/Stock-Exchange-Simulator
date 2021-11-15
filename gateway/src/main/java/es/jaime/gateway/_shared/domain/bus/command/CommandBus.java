package es.jaime.gateway._shared.domain.bus.command;

public interface CommandBus {
    void dispatch(Command command);
}
