package es.jaime.gateway._shared.domain.exceptions;

import es.jaime.gateway._shared.domain.DomainException;

public class IllegalQuantity extends DomainException {
    public IllegalQuantity(String message) {
        super(message);
    }
}
