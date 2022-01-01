package es.jaime.exchange.domain.models.messages;

public interface EventMessage extends Message{
    @Override
    default MessageTypes type(){
        return MessageTypes.EVENT;
    }
}
