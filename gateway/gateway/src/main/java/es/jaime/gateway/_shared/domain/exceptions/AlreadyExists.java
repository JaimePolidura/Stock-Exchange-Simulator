package es.jaime.gateway._shared.domain.exceptions;


import es.jaime.gateway._shared.domain.DomainException;

public class AlreadyExists extends DomainException {
    public AlreadyExists(String message) {
        super(message);
    }
}
