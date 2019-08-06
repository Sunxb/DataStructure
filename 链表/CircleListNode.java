package com.sunxb.链表;

/**
 * 结点类
 * @author sunxiaobin
 *
 * @param <T>
 */
public class CircleListNode <T> {
	T val;
	CircleListNode<T> next;
	CircleListNode<T> prev;
	
	CircleListNode(T x) { val = x; }
	
	private void printListNodeValue() {
		CircleListNode<T> node = this;
		while (node != null) {
			System.out.println(node.val);
			node = node.next;
		}
	}
	
}
