package com.sunxb.Trie;

import java.util.HashMap;

public class Trie<V> {
    private int size;
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }


    public V add(String key, V value) {
        keyCheck(key);

        if (root == null) {
            root = new Node<>(null);
        }

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i ++) {
            char c = key.charAt(i);
            // 是否有子结点
            boolean emptyChildren = node.children == null;
            // 子结点 (如果有子结点 就看看当前循环到的这个字符 存不存在-
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                if (emptyChildren)  node.children = new HashMap<>();
                node.children.put(c,childNode);
            }
            node = childNode;
        }

        // 走到这里node就代表 key的最后一个字符
        // 如果已经是某个单词的结尾
        if (node.word) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 新增这个单词
        node.word = true;
        node.value = value;
        size ++;
        return null;

    }


    public V remove(String key) {
        // 找到key的最后一个结点
        Node<V> node = node(key);
        //node == null 代表没有这个key  node.word == false 代表有这个key但是这个key只是某个单词的一部分  并不是独立的单词
        if (node == null || node.word == false) return null;

        size --;
        V oldVlaue = node.value;

        // 如果node还有子结点, 就说明还有一个更长的单词包括这个key这部分
        if (node.children != null && !node.children.isEmpty()) {
            // 只需要改这个key的最后一个结点
            node.word = false;
            node.value = null;
            return oldVlaue;
        }
        // 没有子节点 - 就得顺着往上删除
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }

        return oldVlaue;
    }

    public boolean contains(String str) {
        Node<V> node = node(str);
        return node!=null && node.word;
    }


    public boolean startWith(String prefix) {
        return node(prefix) != null;
    }

    /**
     * 找到最后一个结点
     * @param key
     * @return
     */
    private Node<V> node(String key) {
        keyCheck(key);

        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i< len; i ++) {
            if (node == null || node.children == null || node.children.isEmpty()) return null;
            char c = key.charAt(i);
            node = node.children.get(c);
        }
        return node;

    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V> {
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        Character character;
        V value;
        boolean word; // 是否是一个单词的结尾

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}
