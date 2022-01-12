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
}
