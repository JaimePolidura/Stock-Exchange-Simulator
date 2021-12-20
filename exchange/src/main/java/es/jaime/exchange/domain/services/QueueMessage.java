package es.jaime.exchange.domain.services;

import java.io.Serializable;

public interface QueueMessage extends Serializable {
    String toJson();
}
