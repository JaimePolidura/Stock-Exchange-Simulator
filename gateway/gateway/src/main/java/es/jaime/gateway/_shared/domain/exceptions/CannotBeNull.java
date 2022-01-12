package es.jaime.gateway._shared.domain.exceptions;


import es.jaime.gateway._shared.domain.DomainException;

public class CannotBeNull extends DomainException {
    public CannotBeNull(String message) {
        super(message);
    }
}
