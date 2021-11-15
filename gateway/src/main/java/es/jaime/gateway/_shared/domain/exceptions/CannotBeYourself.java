package es.jaime.gateway._shared.domain.exceptions;

import es.jaime.gateway._shared.domain.DomainException;

public class CannotBeYourself extends DomainException {
    public CannotBeYourself(String message) {
        super(message);
    }
}
