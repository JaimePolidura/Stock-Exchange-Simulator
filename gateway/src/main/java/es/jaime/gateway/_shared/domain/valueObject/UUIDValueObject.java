package es.jaime.gateway._shared.domain.valueObject;


import java.util.Objects;
import java.util.UUID;

public class UUIDValueObject extends ValueObject {
    private final UUID uuid;

    public UUIDValueObject(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID value(){
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUIDValueObject that = (UUIDValueObject) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
