package com.sunxb.堆;

import com.mj.printer.BinaryTreeInfo;

import java.util.Comparator;

// 二叉堆
public class BinaryHeap<T> extends AbstractHeap<T> implements BinaryTreeInfo {
    private T[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(T[] elements,Comparator<T> comparator) {
        super(comparator);

        if (elements == null || elements.length == 0) {
            this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        }
        // 为什么在构建函数中会有下面这个操作? 是因为这个方法中的elements是从外部传进来的,这是要把该数组中的数据同步到本类的elements中
        else {
            size = elements.length;
            int capacity = Math.max(elements.length,DEFAULT_CAPACITY);
            this.elements = (T[]) new Object[capacity];
            for (int i = 0; i < elements.length; i ++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }

    public BinaryHeap(T[] elements) {
        this(elements,null);
    }

    public BinaryHeap(Comparator<T> comparator) {
        this(null,comparator);
    }

    public BinaryHeap() {
        this(null,null);
    }



    @Override
    public void clear() {
        for (int i = 0; i < size; i ++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(T element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size] = element;
        siftUp(size);
        size ++;
    }

    /**
     * 获取最顶部的元素
     * @return
     */
    @Override
    public T get() {
        emptyCheck();
        return elements[0];
    }

    /**
     * 删除最顶部的元素
     * @return
     */
    @Override
    public T remove() {
        emptyCheck();

        T oldElement = elements[0];
        // 把最后一个位置上的值先放到0这个位置上 然后开始下滤
        elements[0] = elements[--size];
        elements[size] = null;// 最后一个位置清空
        siftDown(0);
        return oldElement;
    }

    @Override
    public T replace(T element) {
        elementNotNullCheck(element);

        T root = null;
        if (size == 0) {
            elements[0] = element;
            size ++;
        }
        else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }


    /**
     * 批量建堆
     */
    private void heapify() {
        // 自上而下的上滤
//        for (int i = 0; i < size; i ++) {
//            siftUp(i);
//        }

        // 自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i --) {
            siftDown(i);
        }
    }

    /**
     * 上滤
     * @param index
     */
    private void siftUp(int index) {
        T element = elements[index];
        // ==0 就代表是根了 不需要上溢
        // 虽然是数组来存储数据 要和二叉树结合起来思考
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            T parent = elements[parentIndex];
            // 父结点大 直接结束
            if (compare(element,parent) <= 0) break;
            // 走到这里就说明父结点小  先把父结点拿下来  储存好
            elements[index] = parent;
            // 更新结点
            index = parentIndex;
        }
        // 最后把原来的要上溢的结点储存在合适的位置
        elements[index] = element;
    }

    /**
     * 下滤
     * @param index
     */
    private void siftDown(int index) {

        T element = elements[index];
        int half = size >> 1; // 一半
        while (index < half) {
            // 当前结点可能有一个结点(左)或者两个 不可能没有 (叶子结点已被排除
            // 默认比较的结点是左子结点
            int childIndex = (index << 1) + 1;
            T child = elements[childIndex];

            int rightIndex = childIndex + 1;
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
                childIndex = rightIndex;
                child = elements[childIndex];
            }
            // 如果父结点比子结点中大的那个还大 就不需要下滤了 退出循环
            if (compare(element, child) >= 0) break;

            // 如果父结点小
            // 先把子结点拿上去
            elements[index] = child;
            // 更新index
            index = childIndex;
        }

        elements[index] = element;
    }

    /**
     * 数组扩容
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + oldCapacity >> 1; // 1.5倍

        T[] newElements = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i ++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

    }

    /**
     * 元素不能为空的检测
     * @param element
     */
    private void elementNotNullCheck(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    /**
     * 堆结构为null的检测
     */
    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }


    /**
     *
     */

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int)node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int)node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int)node];
    }
}
