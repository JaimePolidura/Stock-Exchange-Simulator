package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.query.Query;
import es.jaime.gateway.authentication._shared.domain.UserName;
import lombok.Getter;

public final class GenerateTokenUserQuery implements Query {
    @Getter private final UserName username;

    public GenerateTokenUserQuery(String username) {
        this.username = UserName.of(username);
    }
}
