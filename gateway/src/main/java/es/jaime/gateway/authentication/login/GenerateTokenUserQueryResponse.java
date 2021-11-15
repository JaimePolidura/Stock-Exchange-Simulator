package es.jaime.gateway.authentication.login;

import es.jaime.gateway._shared.domain.bus.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GenerateTokenUserQueryResponse implements QueryResponse {
    @Getter private final String token;
}
