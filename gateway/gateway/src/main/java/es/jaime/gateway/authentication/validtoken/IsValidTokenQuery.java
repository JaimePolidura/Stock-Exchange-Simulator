package es.jaime.gateway.authentication.validtoken;

import es.jaime.gateway._shared.domain.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IsValidTokenQuery implements Query {
    @Getter private final String userName;
    @Getter  private final String token;
}
