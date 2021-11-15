package es.jaime.gateway._shared.domain.exceptions;


import es.jaime.gateway._shared.domain.DomainException;

public class AlreadyOwner extends DomainException {
    public AlreadyOwner(String message) {
        super(message);
    }
}
