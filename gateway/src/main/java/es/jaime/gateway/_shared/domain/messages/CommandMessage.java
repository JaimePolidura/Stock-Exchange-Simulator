package es.jaime.gateway._shared.domain.messages;

public interface CommandMessage extends Message{
    @Override
    default MessageTypes type() {
        return MessageTypes.COMMAND;
    }
}
