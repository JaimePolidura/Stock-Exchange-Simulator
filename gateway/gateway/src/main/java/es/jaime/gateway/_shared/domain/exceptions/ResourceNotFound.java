package es.jaime.gateway._shared.domain.exceptions;

import es.jaime.gateway._shared.domain.DomainException;

public class ResourceNotFound extends DomainException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
