package es.jaime.gateway._shared.domain.exceptions;

import es.jaime.gateway._shared.domain.DomainException;

public class IllegalType extends DomainException {
    public IllegalType(String message) {
        super(message);
    }
}
