package es.jaime.gateway._shared.domain;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    private Utils() {}

    @SafeVarargs
    public static <E> List<E> toList(List<E> ...elements){
        List<E> toReturn = new ArrayList<>();

        for (List<E> element : elements) {
            toReturn.addAll(element);
        }

        return toReturn;
    }
}
