package com.sunxb.二叉查找树;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.mj.printer.BinaryTreeInfo;
import com.mj.printer.BinaryTrees;
import com.mj.printer.Printer;

public class BinarySearchTree<T> implements BinaryTreeInfo {
	
	private int size;
	private Node<T> root;
	private Comparator<T> comparetor;
	
	
	// 构造函数
	public BinarySearchTree(Comparator<T> comparetor) {
		this.comparetor = comparetor;
	}
	
	public BinarySearchTree() {
		this.comparetor = null;
	}
	
	// public method
	public int size() {
		return size;
	}
	
	/*
	 * 添加元素
	 * */
	public void add(T element) {
		elementNotNullCheck(element);
		
		if (size == 0) {
			// 第一个节点
			root = new Node<T>(element, null);
		}
		else {
			// 其他节点
			addElementToNode2(element, root);
		}
		size ++;
	}
	
	// 递归 (感觉效率低一些了)
	private void addElementToNode(T element, Node<T> node) {
		
		int result = compare(element, node.element);
		
		if (result > 0) { // element 比 节点的值 大
			if (node.right != null) {
				// 有右节点
				addElementToNode(element, node.right);
			}
			else {
				node.right = new Node<>(element, node);
				return;
			}
		}
		
		else if (result < 0){// element 比 节点的值 小
			if (node.left != null) {
				// 有左节点
				addElementToNode(element, node.left);
			}
			else { 
				node.left = new Node<>(element, node);
				return;
			}
		}
		
		else {
			node.element = element;
			return;
		}
		
	}
	// 迭代
	private void addElementToNode2(T element, Node<T> node) {
		
		int result = 0;
		Node<T> parent;
		
		do {
			parent = node;
			result = compare(element, node.element);
			
			if (result > 0) {
				node = node.right;
			}
			else if (result < 0) {
				node = node.left;
			}
			else {
				node.element = element;
				return;
			}
		} while (node != null);
		
		if (result > 0) {
			parent.right = new Node<T>(element, parent);
		}
		else if (result < 0) {
			parent.left = new Node<T>(element, parent);
		}
		else {	
		}
	}
	
	
	public T remove(T element) {
		return null;
	}
	
	public boolean contain(T element) {
		return false;
	}
	
	public void clear() {
		
	}
	
	private void elementNotNullCheck(T element) {
		if (element == null) {
			throw new IllegalArgumentException("element not be null");
		}
	}

	
	/*
	 * 前序遍历
	 * */ 
	private List<T> preorderTraversal(Node<T> node) {

		List<T> list = new ArrayList<>();
		preorderTraversal_(list, node);
		return list;
	}
	
	private void preorderTraversal_(List<T> list, Node<T> node) {
		if (node == null) return;
		
		list.add(node.element);
		preorderTraversal_(list, node.left);
		preorderTraversal_(list, node.right);		
		
	}
	
	/*
	 * 中序遍历
	 * */
	private List<T> inorderTraversal(Node<T> node) {
		List<T> list = new ArrayList<>();
		inorderTraversal_(list, node);
		return list;
	}
	
	private void inorderTraversal_(List<T> list, Node<T> node) {
		if (node == null) return;
		
		inorderTraversal_(list, node.left);
		list.add(node.element);
		inorderTraversal_(list, node.right);
		
	}
	
	/*
	 * 后序遍历
	 * */
	private List<T> postorderTraversal(Node<T> node) {
		List<T> list = new ArrayList<>();
		postorderTraversal_(list, node);
		return list;
	}
	
	private void postorderTraversal_(List<T> list, Node<T> node) {
		if (node == null) return;
		
		postorderTraversal_(list, node.left);
		postorderTraversal_(list, node.right);
		list.add(node.element);
	}
	
	/*
	 * 层序遍历
	 * */
	private List<T> levelOrderTraversal(Node<T> node) {
		List<T> list = new ArrayList<>();
		
		if (node == null) return list;

		Queue<Node<T>> queue = new LinkedList<>();
		queue.offer(node);
		
		while (!queue.isEmpty()) {
			Node<T> n = queue.poll();
			list.add(n.element);
			
			if (n.left != null) queue.offer(n.left);
			if (n.right != null) queue.offer(n.right);
		}
		
		return list;
	}
	
	
	
	/*
	 * 比较大小
	 * */
	private int compare(T e1, T e2) {
		if (comparetor != null) {
			return comparetor.compare(e1, e2);
		}
		
		return ((Comparable<T>)e1).compareTo(e2);
	}

	/*
	 * 内部类 node
	 * */
	private static class Node <T>{
		T element;
		Node<T> left;
		Node<T> right;
		Node<T> parent;
		
		public Node(T element, Node<T> parent) {
			this.element = element;
			this.parent = parent;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return element+"";
		}
		
	}
	
	//
	public static void main(String[] args) {
		
		Integer[] arr = new Integer[] {
			20, 10, 30, 5, 33, 25, 29, 6, 4
		};
		
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		for (int i = 0; i < arr.length; i++) {			
			binarySearchTree.add(arr[i]);
		}
		
		BinaryTrees.println(binarySearchTree);
		
		List l = binarySearchTree.preorderTraversal(binarySearchTree.root);
		System.out.println(l);
		
		List l2 = binarySearchTree.inorderTraversal(binarySearchTree.root);
		System.out.println(l2);
		
		List l3 = binarySearchTree.postorderTraversal(binarySearchTree.root);
		System.out.println(l3);
		
		List l4 = binarySearchTree.levelOrderTraversal(binarySearchTree.root);
		System.out.println(l4);
		
//		System.out.println("---");
//		
//		System.out.println(binarySearchTree.root.right.left.right.element);
//		System.out.println(binarySearchTree.root.right.left.right.parent.element);
	}

	
	
	////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Object root() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public Object left(Object node) {
		// TODO Auto-generated method stub
		return ((Node<T>)node).left;
	}

	@Override
	public Object right(Object node) {
		// TODO Auto-generated method stub
		return ((Node<T>)node).right;
	}

	@Override
	public Object string(Object node) {
		// TODO Auto-generated method stub
		return ((Node<T>)node).toString();
	}
	
	
	
	
	
	
}
