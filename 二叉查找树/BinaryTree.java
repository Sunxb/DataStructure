package com.sunxb.二叉查找树;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.mj.printer.BinaryTreeInfo;


public class BinaryTree<T> implements BinaryTreeInfo {
	protected int size;
	protected Node<T> root;
	protected Comparator<T> comparetor;
	
	public int size() {
		return size;
	}
	
	/*
	 * 二叉树高度
	 * */
	public int height() {
		
		if (root == null) return 0;
		
		// 利用层序遍历
		Queue<Node<T>> queue = new LinkedList<>();
		queue.offer(root);
		
		int height = 0;
		int index = 1; // 层数
		
		while (!queue.isEmpty()) {
			Node<T> node = queue.poll();
			index --;
				
			if (node.left != null) queue.offer(node.left);
			if (node.right != null) queue.offer(node.right);
			
			if (index == 0) {
				index = queue.size();
				height ++;
			}
		}
		return height;
	}
	
	public int height2() {
		return height(root);
	}
	
	// 递归
	private int height(Node<T> node) {
		if (node == null) return 0;
		return 1+Math.max(height(node.left), height(node.right));
	}
	
	/**
	 * @param node
	 * @return 是否是完全二叉树
	 */
	public boolean isComplete() {
		
		if (root == null) return false;

		Queue<Node<T>> queue = new LinkedList<>();
		queue.offer(root);
		
		boolean leaf = false;// 标识当前节点后面的就要是叶子了(层序遍历)
		
		while (!queue.isEmpty()) {
			
			Node<T> n = queue.poll();
			if (leaf && !n.isLeaf()) return false;
			
			if (n.left != null) {
				queue.offer(n.left);
			}
			else if (n.right != null) { // node.left == null && node.right != null
				return false;
			}
			
			// 
			if (n.right != null) { // node.left != null && node.right != null
				queue.offer(n.right);
			}
			else {
				leaf = true;
			}
		}
		
		return true;
	}
	
	/*
	 * 根据element找到对应的结点
	 * 
	 * 最开始的思路是使用层序遍历,一个一个的找,然后比较,找到目标node, 如果是普通的二叉树这样没问题
	 * 如果是二叉查找树,这样效率比较低,其实完全可以按照特性左小右大,很快就可以找到目标node
	 * */
	public Node<T> node(T element) { // 层序遍历方法
		elementNotNullCheck(element);
		
		if (root == null) return null;

		Queue<Node<T>> queue = new LinkedList<>();
		queue.offer(root);
		
		while (!queue.isEmpty()) {
			Node<T> n = queue.poll();
			
			if (compare(element, n.element) == 0) {
				return n;
			}
			
			if (n.left != null) queue.offer(n.left);
			if (n.right != null) queue.offer(n.right);
		}
		
		return null;
	}
	
	/*
	 * 二叉查找树某元素的前驱节点 
	 * 
	 * (中序遍历结果的前一个结点,因为中序遍历的结果就是升序)
	 * */
	public Node<T> preNode(T element) {
		if (element == null) return null;
		
		Node<T> node = node(element);
		
		// 1. 有左子树
		if (node.left != null) {
			// 前驱 就是左子树里面最右边的node  .left.right.right...
			node = node.left;
			while (node.right != null) {
				node = node.right;
			}
			return node;
		}
		
		// 2. 没有左子树
		// 2.1 没有父节点
		// 下面代码可以注销掉  因为后面的循环可以兼容这个代码
//		if (node.parent == null) { // 没有父节点说明是根结点, 同时没有左子树, 所以根节点没有前驱
//			return null;
//		}
		
		// 2.2 有父节点
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		return node.parent;
	
	}
	
	
	/*
	 * 前序遍历: 递归
	 * */ 
	protected List<T> preorderTraversal() {

		List<T> list = new ArrayList<>();
		preorderTraversal_(list, root);
		return list;
	}
	
	private void preorderTraversal_(List<T> list, Node<T> node) {
		if (node == null) return;
		
		list.add(node.element);
		preorderTraversal_(list, node.left);
		preorderTraversal_(list, node.right);		
	}
	
	/**
	 * 前序遍历: 迭代
	 * 
	 * 利用栈的特性,先进后出, 先往里放right node
	 */
	protected List<T> preorderTraversal2() {

		List<T> list = new ArrayList<>();
		Stack<Node<T>> stack = new Stack<Node<T>>();
		stack.push(root);
		
		while (!stack.isEmpty()) {
			Node<T> n = stack.pop();
			list.add(n.element);
			if (n.right != null) stack.push(n.right);
			if (n.left != null) stack.push(n.left);
		}
		
		return list;
	}
	
	
	/*
	 * 中序遍历
	 * */
	protected List<T> inorderTraversal() {
		List<T> list = new ArrayList<>();
		inorderTraversal_(list, root);
		return list;
	}
	
	private void inorderTraversal_(List<T> list, Node<T> node) {
		if (node == null) return;
		
		inorderTraversal_(list, node.left);
		list.add(node.element);
		inorderTraversal_(list, node.right);
		
	}
	
	/*
	 * 中序遍历: 迭代
	 * */
	protected List<T> inorderTraversal2() {
		List<T> list = new ArrayList<>();
		Stack<Node<T>> stack = new Stack<Node<T>>();
		stack.push(root);
		
		while (!stack.isEmpty()) {	
			Node<T> n = stack.pop();
			
			if (!list.contains(n.element)) {
				if (n.left != null) {
					if (!list.contains(n.left.element)) {
						stack.push(n);
						stack.push(n.left);
						
						continue;
					}					
				}
				
				list.add(n.element);
				if (n.right != null) {
					stack.push(n.right);
				}
			}
			
			
			else {
				if (n.right != null) {
					if (!list.contains(n.right.element)) {
						stack.push(n.right);
					}		
				}
			}
		}
		
		
		return list;
	}
	
	
	/*
	 * 后序遍历
	 * */
	protected List<T> postorderTraversal(Node<T> node) {
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
	protected int compare(T e1, T e2) {
		if (comparetor != null) {
			return comparetor.compare(e1, e2);
		}
		
		return ((Comparable<T>)e1).compareTo(e2);
	}
	
	/*
	 * 元素null监测
	 * */
	protected void elementNotNullCheck(T element) {
		if (element == null) {
			throw new IllegalArgumentException("element not be null");
		}
	}
	
	
	/*
	 * 内部类 node
	 * */
	protected static class Node <T> {
		T element;
		Node<T> left;
		Node<T> right;
		Node<T> parent;
		
		public Node(T element, Node<T> parent) {
			this.element = element;
			this.parent = parent;
		}
		
		public boolean isLeaf() {
			return left == null && right == null;
		}
		
		public boolean hasTwoChildren() {
			return left != null && right != null;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			if (parent == null) {
				return element+"_null";
			}
			return element+"_"+parent.element;
		}
		
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
