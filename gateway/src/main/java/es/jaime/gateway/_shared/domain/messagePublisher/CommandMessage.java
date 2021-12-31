package es.jaime.gateway._shared.domain.messagePublisher;

public interface CommandMessage extends Message{
    @Override
    default MessagesTypes type() {
        return MessagesTypes.COMMAND;
    }
}
