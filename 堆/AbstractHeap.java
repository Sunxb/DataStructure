package com.sunxb.堆;

import java.util.Comparator;

public abstract class AbstractHeap<T> implements Heap<T> {
    protected int size;
    protected Comparator<T> comparator;

    public AbstractHeap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public AbstractHeap() {
        this(null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    protected int compare(T t1, T t2) {
        return comparator != null ? comparator.compare(t1,t2) : ((Comparable)t1).compareTo(t2);
    }
}
