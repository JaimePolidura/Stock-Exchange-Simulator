package es.jaimetruman.exchangedistribuitor._shared;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class LimitOrderExecutionType extends OrderExecutionType{
    @Getter private final double price;
}
