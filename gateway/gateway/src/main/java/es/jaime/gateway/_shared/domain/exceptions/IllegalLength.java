package es.jaime.gateway._shared.domain.exceptions;


import es.jaime.gateway._shared.domain.DomainException;

public class IllegalLength extends DomainException {
    public IllegalLength(String message) {
        super(message);
    }
}
