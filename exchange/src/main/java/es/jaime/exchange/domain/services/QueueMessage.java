package es.jaime.exchange.domain;

import java.io.Serializable;

public interface QueueMessage extends Serializable {
    String toJson();
}
