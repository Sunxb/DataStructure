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
	
	/*
	 * 删除某一个元素对应的结点
	 * 
	 * 分为移除度为0,1,2的三种情况
	 * */
	public void remove(T element) {
		
		Node<T> node = node(element);
		// 度为2 找到他的前驱结点  把前驱结点的值 放到要删除节点上面 最后转化为删除这个前驱节点
		// 前驱的度一定是0或1,肯定不为2
		if (node.hasTwoChildren()) {
			// 找到前驱结点
			Node<T> preNode = preNode(element);
			node.element = preNode.element;
			node = preNode;
		}
				
		// 走到这个位置 node的度为1或0
		Node<T> replacement = node.left != null ? node.left : node.right;
		if (replacement != null) {
			replacement.parent = node.parent;
		}
		
		
		if (node.parent == null) { // 根结点
			root = replacement;
			replacement.parent = null;
		}
		else if (node.isLeaf()) { // 不是根节点 , 叶子结点 度为0  replacement== null
			if (node == node.parent.left) {
				node.parent.left = null;
			}
			else { // (node == node.parent.right)
				node.parent.right = null;
			}
			node.parent = null;
		}
		else { // 度为1
			if (node == node.parent.left) {
				node.parent.left = replacement;
			}
			else { // (node == node.parent.right)
				node.parent.right = replacement;
			}
		}
		
		size --;
	}
	
	public boolean contain(T element) {
		return false;
	}
	
	public void clear() {
		
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
	
	//
	public static void main(String[] args) {
		
		Integer[] arr = new Integer[] {
//			20, 10, 30, 5, 33, 25, 6, 4, 24
				2,1,3,4,5,6,8,7
		};
		
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		for (int i = 0; i < arr.length; i++) {			
			binarySearchTree.add(arr[i]);
		}
		
		BinaryTrees.println(binarySearchTree);
//		
		binarySearchTree.remove(1);
		BinaryTrees.println(binarySearchTree);
//		
		binarySearchTree.remove(2);
		BinaryTrees.println(binarySearchTree);
//		
//		binarySearchTree.remove(20);
//		BinaryTrees.println(binarySearchTree);
//		
//		binarySearchTree.remove(30);
//		BinaryTrees.println(binarySearchTree);
//		
//		binarySearchTree.add(30);
//		binarySearchTree.add(20);
//		BinaryTrees.println(binarySearchTree);
		
//		List l = binarySearchTree.preorderTraversal(binarySearchTree.root);
//		System.out.println(l);
//		
//		List l2 = binarySearchTree.inorderTraversal(binarySearchTree.root);
//		System.out.println(l2);
//		
//		List l3 = binarySearchTree.postorderTraversal(binarySearchTree.root);
//		System.out.println(l3);
//		
//		List l4 = binarySearchTree.levelOrderTraversal(binarySearchTree.root);
//		System.out.println(l4);
		
//		System.out.println(binarySearchTree.isComplete());
//		Node<Integer> preNode = binarySearchTree.preNode(33);
//		if (preNode == null) {
//			System.out.println("null -- ");
//		}
//		else {
//			System.out.println(preNode.element);
//		}
		
		
		System.out.println("高度:"+binarySearchTree.height());
		System.out.println("高度:"+binarySearchTree.height2());
		

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
