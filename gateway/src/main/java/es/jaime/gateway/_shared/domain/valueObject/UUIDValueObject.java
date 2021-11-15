package es.jaime.gateway._shared.domain.valueObject;


import java.util.UUID;

public class UUIDValueObject extends ValueObject {
    private final UUID uuid;

    public UUIDValueObject(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID value(){
        return this.uuid;
    }
}
