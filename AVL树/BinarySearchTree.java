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
			root = createNode(element, null);
			afterAdd(root);
		}
		else {
			// 其他节点
			addElementToNode(element, root);
		}
		size ++;
		
		
	}
	
	// 递归 (感觉效率低一些了)
//	private void addElementToNode(T element, Node<T> node) {
//		
//		int result = compare(element, node.element);
//		
//		if (result > 0) { // element 比 节点的值 大
//			if (node.right != null) {
//				// 有右节点
//				addElementToNode(element, node.right);
//			}
//			else {
//				node.right = new Node<>(element, node);
//				return;
//			}
//		}
//		
//		else if (result < 0){// element 比 节点的值 小
//			if (node.left != null) {
//				// 有左节点
//				addElementToNode(element, node.left);
//			}
//			else { 
//				node.left = new Node<>(element, node);
//				return;
//			} 
//		}
//		
//		else {
//			node.element = element;
//			return;
//		}
//		
//	}
	// 迭代
	private void addElementToNode(T element, Node<T> node) {
		
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
		
		Node<T> newNode = createNode(element, parent);
		if (result > 0) {
			parent.right = newNode;
		}
		else if (result < 0) {
			parent.left = newNode;
		}
		else {	
		}
		
		// 如果node.element = element; 不会执行这句代码, 不过不碍事, 相等的情况只是覆盖, 没有添加新的结点
		afterAdd(newNode);
	}
	
	protected void afterAdd(Node<T> node) {
		
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
	
}
