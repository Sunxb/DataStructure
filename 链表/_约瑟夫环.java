package com.sunxb.链表;

/**
 * 约瑟夫环 简略版
 * @author sunxiaobin
 *
 */
public class _约瑟夫环 {
	
	static class CircleList <T> {
		private  T value;
		private int size;
		private CircleListNode<T> first;
		private CircleListNode<T> last;
		
		// add 添加到最后位置
		public void addElement(T element) {
			
			if (size == 0) {
				// 第一个元素
				CircleListNode<T> newNode = new CircleListNode<T>(element);
				newNode.next = newNode;
				newNode.prev = newNode;
				first = newNode;
				last = newNode;
				
			}
			
			else {
				CircleListNode<T> prev = searchElementAtIndex(size-1);
				CircleListNode<T> newNode = new CircleListNode<T>(element);
				CircleListNode<T> firstNode = first;
				
				prev.next = newNode;
				firstNode.prev = newNode;
				
				newNode.prev = prev;
				newNode.next = firstNode;
				
				last = newNode;
			}
			
			size ++;
		}
		
		// 根本没用到
		public void removeElementAtIndex(int index) {
			CircleListNode<T> oldNode = searchElementAtIndex(index);
			CircleListNode<T> prev = oldNode.prev;
			CircleListNode<T> next = oldNode.next;
			
			prev.next = next;
			next.prev = prev;
			
			if (index == 0) { // fist one
				first = next;
			}
			else if (index == size-1) { // last one
				last = prev;
			}
			else { // others
				
			}
			
			size --;
		}
		
		
		
		private CircleListNode<T> searchElementAtIndex(int index) {
			boundCheck(index);
			if (index < (size << 1)) {
				// ->
				CircleListNode<T> node = first;
				for (int i = 0; i < index; i++) {
					node = node.next;
				}
				return node;
				
			}
			else {
				// <-
				CircleListNode<T> node = last;
				for (int i = size-1; i > index; i++) {
					node = node.prev;
				}
				return node;
			}
			
		}
		
		private void boundCheck(int index) {
			if (index < 0 || index >= size) {
				throw new RuntimeException("数组越界");
			}
		}
		
	}
	
	public static void main(String[] args) {
		CircleList<Integer> list = new CircleList<Integer>();
		
		list.addElement(1);
		list.addElement(2);
		list.addElement(3);
		list.addElement(4);
		list.addElement(5);
		list.addElement(6);
		list.addElement(7);
		list.addElement(8);
		list.addElement(9);
		
		// 每个node的index等于他的值value-1
		
		int index = 0;
		
		CircleListNode<Integer> n = list.first;
		
		while (list.size > 1) {
			++ index;
			CircleListNode<Integer> nextNode = n.next;
			if (index == 3) {
				// remove
				CircleListNode<Integer> prevNode = n.prev;
				prevNode.next = nextNode;
				nextNode.prev = prevNode;
				//
				index = 0;
				System.out.println("干掉"+n.val);
				n = nextNode;
				
				list.size --;
			}
			
			else {
				n = n.next;
			}

		}
		
		CircleListNode<Integer> node = n;
		for (int i = 0; i < list.size; i++) {
			System.out.println(node.val);
			node = node.next;
		}
		
		
	}
}
