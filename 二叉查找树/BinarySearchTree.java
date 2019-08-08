package com.sunxb.二叉查找树;

import java.util.Comparator;
//import com.mj.printer.BinaryTrees;

public class BinarySearchTree<T> extends BinaryTree<T> {
	
	// 构造函数
	public BinarySearchTree(Comparator<T> comparetor) {
		this.comparetor = comparetor;
	}
	
	public BinarySearchTree() {
		this(null);
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
		return node(element) != null;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	

	

	
	


	
	
	
	
	


	
	
	//
	public static void main(String[] args) {
		
//		Integer[] arr = new Integer[] {
//			20, 10, 30, 5, 33, 25, 6, 4, 24
//		};
//		
//		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
//		for (int i = 0; i < arr.length; i++) {			
//			binarySearchTree.add(arr[i]);
//		}
//		
//		BinaryTrees.println(binarySearchTree);

		
//		binarySearchTree.remove(4);
//		BinaryTrees.println(binarySearchTree);
//		
//		binarySearchTree.remove(25);
//		BinaryTrees.println(binarySearchTree);
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
		
		
//		System.out.println("高度:"+binarySearchTree.height());
//		System.out.println("高度:"+binarySearchTree.height2());
		

	}

	
	

	
	
	
	
	
}
