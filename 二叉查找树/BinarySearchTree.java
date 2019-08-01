package com.sunxb.二叉查找树;

public class BinarySearchTree<T extends Comparable<? super T>> {
	
	private int size;
	private Node<T> root;
	
	public int size() {
		return size;
	}
	
	public void add(T element) {
		if (size == 0) {
			// 第一个节点
			root = new Node<T>(element, null);
		}
		else {
			// 其他节点
			addElementToNode(element, root);
		}
		
		size ++;
	}
	
	public T remove(T element) {
		return null;
	}
	
	public boolean contain(T element) {
		return false;
	}
	
	public void clear() {
		
	}
	
	// private method
//	private boolean () {
//		
//	}
	
	private void addElementToNode(T element, Node<T> node) {
		int result = element.compareTo(node.element);
		if (result >= 0) { // element 比 节点的值 大
			if (node.right != null) {
				// 有右节点
				addElementToNode(element, node.right);
			}
			else {
				node.right = new Node<>(element, node);
				return;
			}
		}
		
		else {// element 比 节点的值 小
			if (node.left != null) {
				// 有左节点
				addElementToNode(element, node.left);
			}
			else {
				node.left = new Node<>(element, node);
				return;
			}
		}
		
	}
	
	/////// 
	private static class Node <T> {
		T element;
		Node<T> left;
		Node<T> right;
		Node<T> parent;
		
		public Node(T element, Node<T> parent) {
			this.element = element;
			this.parent = parent;
		}
	}
	
	//
	public static void main(String[] args) {
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		binarySearchTree.add(20);
		binarySearchTree.add(10);
		binarySearchTree.add(30);
		binarySearchTree.add(5);
		binarySearchTree.add(33);
		binarySearchTree.add(25);
		binarySearchTree.add(29);
		System.out.println("---");
		
		System.out.println(binarySearchTree.root.right.left.right.element);
		System.out.println(binarySearchTree.root.right.left.right.parent.element);
	}
	
	
	// 前序遍历
	
	
}
