package es.jaime.exchange.domain.models.messages;

public interface CommandMessage extends Message{
    @Override
    default MessageTypes type() {
        return MessageTypes.COMMAND;
    }
}
