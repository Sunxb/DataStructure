package com.sunxb.哈希表;

import java.util.Objects;

public class LinkedHashMap<K,V> extends HashMap<K,V> {
    // 构建一条双向链表
    private LinkedNode<K,V> first;
    private LinkedNode<K,V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value, node.value)) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        LinkedNode<K,V> node = first;
        while (node != null) {
            if (visitor.visit(node.key,node.value)) return;
            node = node.next;
        }
    }

    /**
     * 删除结点之后 需要维护first last  还有某些结点的prev next
     * @param willNode
     * @param removedNode
     */
    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        LinkedNode<K,V> node1 = (LinkedNode<K,V>)willNode;
        LinkedNode<K,V> node2 = (LinkedNode<K,V>)removedNode;

        if (node1 != node2) {
            // 交换node1和node2在链表中的位置
            // 1.交换prev
            LinkedNode<K,V> tmp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = tmp;
            if (node1.prev == null) {
                first = node1;
            }
            else {
                // 现在的node1.prev其实是原来的node2.prev 这个prev的next原指向node2  要更新
                node1.prev.next = node1;
            }
            if (node2.prev == null) {
                first = node2;
            }
            else {
                node2.prev.next = node2;
            }
            //2.交换next
            tmp = node1.next;
            node1.next = node2.next;
            node2.next = tmp;
            if (node1.next == null) {
                last = node1;
            }
            else {
                node1.next.prev = node1;
            }
            if (node2.next == null) {
                last = node2;
            }
            else {
                node2.next.prev = node2;
            }
        }

        LinkedNode<K,V> prev = node2.prev;
        LinkedNode<K,V> next = node2.next;

        if (prev == null) {
            first = next;
        }
        else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        }
        else {
            next.prev = prev;
        }

    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K,V> node = new LinkedNode<>(key,value,parent);

        // 在构建node的时候就把prev和next串起来
        if (first == null) {
            first = last = node;
        }
        else {
            last.next = node;
            node.prev = last;
            last = node;
        }
        return node;
    }

    private static class LinkedNode<K,V> extends Node<K,V> {
        LinkedNode<K,V> prev;
        LinkedNode<K,V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }


    }
}
