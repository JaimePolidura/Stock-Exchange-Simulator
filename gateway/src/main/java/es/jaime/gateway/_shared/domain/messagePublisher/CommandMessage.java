package es.jaime.gateway._shared.domain.messagePublisher;

public interface CommandMessage extends Message{
    @Override
    default MessageTypes type() {
        return MessageTypes.COMMAND;
    }
}
