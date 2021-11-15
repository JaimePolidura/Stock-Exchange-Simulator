package es.jaime.gateway.authentication._shared.domain;

import es.jaime.gateway._shared.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public final class User extends Aggregate {
    @Getter private final UserName username;
    @Getter private final UserPassword userPassword;

    @Override
    public Map<String, Object> toPrimitives() {
        return new HashMap<>(){{
            put("username", username.value());
            put("password", userPassword.value());
        }};
    }

    @Override
    public User fromPrimitives(Map<String, Object> values) {
        return new User(new UserName(String.valueOf(values.get("username"))), new UserPassword(String.valueOf(values.get("password"))));
    }
}
