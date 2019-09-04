package com.sunxb.优先级队列;

import com.sunxb.堆.BinaryHeap;
import com.sunxb.堆.Heap;

import java.util.Comparator;

public class PriorityQueue<T> {

    private Heap<T> heap;

    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator comparator) {
        heap = new BinaryHeap<T>(comparator);
    }


    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void enQueue(T element) {
        heap.add(element);
    }

    public T deQueue() {
        return heap.remove();
    }

    /**
     * 获取队列头元素
     * @return
     */
    public T front() {
        return heap.get();
    }

    public void clear() {
        heap.clear();
    }
}
