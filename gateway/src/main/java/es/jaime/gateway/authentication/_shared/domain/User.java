package es.jaime.gateway.authentication._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class User extends Aggregate {
    @Getter private final UserName username;
    @Getter private final UserPassword password;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "username", username.value(),
                "password", password.value()
        );
    }
}
