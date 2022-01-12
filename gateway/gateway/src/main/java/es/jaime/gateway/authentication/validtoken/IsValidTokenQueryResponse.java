package es.jaime.gateway.authentication.validtoken;

import es.jaime.gateway._shared.domain.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IsValidTokenQueryResponse implements QueryResponse {
    @Getter private final Boolean isValid;
}
