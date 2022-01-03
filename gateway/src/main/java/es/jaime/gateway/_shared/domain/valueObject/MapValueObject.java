package es.jaime.gateway._shared.domain.valueObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapValueObject<K, V> extends ValueObject{
    protected final Map<K, V> value;

    public MapValueObject(Map<K, V> value) {
        this.value = value;
    }

    public Map<K, V> value() {
        return new HashMap<>(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapValueObject<?, ?> that = (MapValueObject<?, ?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
