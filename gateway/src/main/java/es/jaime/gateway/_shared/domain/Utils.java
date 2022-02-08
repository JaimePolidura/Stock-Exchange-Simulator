package es.jaime.gateway._shared.domain;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    //A - B
    public static <E> List<E> listDifference(List<E> a, List<E> b){
        List<E> toReturn = new ArrayList<>();

        for (E elementInA : a) {
            if(!b.contains(elementInA)){
                toReturn.add(elementInA);
            }
        }

        return toReturn;
    }

    public static <E> List<E> listUnion(List<E> a, List<E> b){
        List<E> toReturn = new ArrayList<>(a);

        for (E elementInB : b) {
            if(!toReturn.contains(elementInB)){
                toReturn.add(elementInB);
            }
        }

        return toReturn;
    }

    @SneakyThrows
    public static void sleep(long millis) {
        Thread.sleep(millis);
    }
}
