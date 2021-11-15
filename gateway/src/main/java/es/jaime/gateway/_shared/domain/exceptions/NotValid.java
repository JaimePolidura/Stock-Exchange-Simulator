package es.jaime.gateway._shared.domain.exceptions;

import es.jaime.gateway._shared.domain.DomainException;

public class NotValid extends DomainException {
    public NotValid(String message) {
        super(message);
    }
}
