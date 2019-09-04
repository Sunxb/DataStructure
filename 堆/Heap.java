package com.sunxb.堆;

public interface Heap<T> {
    int size();	// 元素的数量
    boolean isEmpty();	// 是否为空
    void clear();	// 清空
    void add(T element);	 // 添加元素
    T get();	// 获得堆顶元素
    T remove(); // 删除堆顶元素
    T replace(T element); // 删除堆顶元素的同时插入一个新元素
}
