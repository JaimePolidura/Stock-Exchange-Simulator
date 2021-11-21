package es.jaime.gateway._shared.domain.exceptions;

import es.jaime.gateway._shared.domain.DomainException;

public final class IllegalAccess extends DomainException {
    public IllegalAccess(String message) {
        super(message);
    }
}
