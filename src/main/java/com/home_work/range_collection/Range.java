package com.home_work.range_collection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Range<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable {

    private transient HashMap<E, Object> map;

    private static final Object PRESENT = new Object();

    public Range() {
        this.map = new HashMap<>();
    }

    @Override
    public Iterator iterator() {
        return this.map.keySet().iterator();
    }


    @Override
    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean contains(Object object) {
        return this.map.containsKey(object);
    }

    public boolean add(E object) {
        return this.map.put(object, PRESENT) == null;
    }

    public boolean remove(Object object) {
        return this.map.remove(object) == PRESENT;
    }

    public void clear() {
        this.map.clear();
    }


    public static <E> Range<E> of(E first, E second) {
        if (first instanceof Integer) {
            return rangeInt((Integer) first, (Integer) second);
        }
        return rangeFloat((Float) first, (Float) second);
    }

    public static <E> Range<E> of(Character first, Character second, Function function) {
        Range range = new Range();
        range.add(first);
        Character temp = first;
        while (!temp.equals(second)) {
            temp = (Character) function.apply(temp);
            range.add(temp);
        }
        return range;
    }

    private static Range rangeInt(Integer first, Integer second) {
        if (first.equals(second)) {
            return new Range();
        }
        Range<Integer> range = new Range<>();
        IntStream.rangeClosed(first, second).forEach(element -> range.add(element));
        return range;
    }

    private static Range rangeFloat(Float first, Float second) {

        Float max = Math.max(roundFloat(first), roundFloat(second));
        Float min = Math.min(roundFloat(first), roundFloat(second));
        Range<Float> range = new Range<>();
        range.add(min);
        while (!min.equals(max)) {
            min += 0.1f;
            range.add(min);
        }
        return range;
    }

    private static Float roundFloat(Float number) {
        BigDecimal bigDecimal = new BigDecimal(number).setScale(1, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }
}