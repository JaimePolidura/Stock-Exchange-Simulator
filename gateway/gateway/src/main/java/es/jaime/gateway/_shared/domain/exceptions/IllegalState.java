package es.jaime.gateway._shared.domain.exceptions;


import es.jaime.gateway._shared.domain.DomainException;

public class IllegalState extends DomainException {
    public IllegalState(String message) {
        super(message);
    }
}
