package com.sunxb.二叉查找树;

import java.util.Comparator;

public class AVLTree<T> extends BinarySearchTree<T> {

	/*
	 * 构造
	 * */
	public AVLTree() {
		this(null);
	}
	
	public AVLTree(Comparator<T> comparetor) {
		super(comparetor);
	}
	
	/*
	 * 添加结点之后执行的操作, 在父类中留了空,AVL树在本方法中实现添加完节点后自平衡的特性
	 * */
	@Override
	protected void afterAdd(Node<T> node) { 
		while ((node = node.parent) != null) {
			if (isBalance(node)) {
				updateHeight(node);
			}
			else {
				// 恢复平衡
				// 这个传进reBalance方法的node至少是原来node的grand parent
				// 把一个结点A作为直接的child添加到在一个结点B下面, B的平衡肯定不会受影响,失衡的最近也是A的grand parent
				reBalance(node);
				break;
			}
		}
	}
	
	@Override
	protected void afterRemove(Node<T> node) {
		while ((node = node.parent) != null) {
			if (isBalance(node)) {
				updateHeight(node);
			}
			else {
				reBalance(node);
			}
		}
	}
	
	@Override
	protected Node<T> createNode(T element, Node<T> parent) {
		return new AVLNode(element, parent);
	}
	
	/*
	 * 结点是否平衡
	 * */
	private boolean isBalance(Node<T> node) {
		return Math.abs(((AVLNode<T>)node).balcanceFactor()) <= 1;
	}
	
	private void updateHeight(Node<T> node) {
		((AVLNode<T>)node).updateHeight();
	}
	
	/*
	 * 恢复平衡
	 * */
	private void reBalance(Node<T> n) {
		AVLNode<T> grand = (AVLNode<T>)n;
		AVLNode<T> parent = grand.tallerChild();
		AVLNode<T> node = parent.tallerChild();
		
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				rotateRight(grand);
			}
			else { //LR
				rotateLeft(parent);
				rotateRight(grand);
			}
		}
		else { // R
			if (node.isLeftChild()) { //RL
				rotateRight(parent);
				rotateLeft(grand);
			}
			else { // RR
				rotateLeft(grand);
			}
		}
	}
	
	/*
	 * 右旋
	 * */
	private void rotateRight(AVLNode<T> grand) {
		
		AVLNode<T> parent = (AVLNode<T>)grand.left;
		AVLNode<T> child = (AVLNode<T>)parent.right;
		
		grand.left = child;
		parent.right = grand;

		afterRotate(grand, parent, child);
	}
	
	/*
	 * 左旋
	 * */
	private void rotateLeft(AVLNode<T> grand) {
		AVLNode<T> parent = (AVLNode<T>)grand.right;
		AVLNode<T> child = (AVLNode<T>)parent.left;
		
		grand.right = child;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	private void afterRotate(AVLNode<T> grand, AVLNode<T> parent, AVLNode<T> child) {
		// 让parent成为根
		parent.parent = grand.parent;
		// 处理各种parent
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		}
		else if (grand.isRightChild()) {
			grand.parent.right = parent;
		}
		else {
			this.root = parent;
		}
		
		if (child != null) {
			child.parent = grand;
		}
		
		grand.parent = parent;
		
		updateHeight(grand);
		updateHeight(parent);
	}
	
	/*
	 * node 内部类
	 * */
	
	private static class AVLNode<T> extends Node<T> {

		public AVLNode(T element, Node<T> parent) {
			super(element, parent);
		}
		
		/*
		 * 直接记录每一个结点的高度, 就不用像之前那样递归求高度了
		 * */
		int height = 1; // 高度
		
		/*
		 * 平衡因子 : 左子树高度-右子树高度
		 * */
		public int balcanceFactor() {
			int leftHeight = left != null ? ((AVLNode<T>)left).height : 0;
			int rightHeight = right != null ? ((AVLNode<T>)right).height : 0;
			return leftHeight - rightHeight;
		}
		
		/*
		 *  获取左右子树中,较高的一棵
		 *  
		 *  如果左右两颗子树高度不同,返回高的那一颗
		 *  如果相同的话,就看当前结点是父节点的左还是右,返回同方向的
		 * */
		public AVLNode<T> tallerChild() {
			int leftHeight = left != null ? ((AVLNode<T>)left).height : 0;
			int rightHeight = right != null ? ((AVLNode<T>)right).height : 0;
			if (leftHeight > rightHeight) {
				return (AVLNode<T>)left;
			}
			else if (leftHeight < rightHeight) {
				return (AVLNode<T>)right;
			}
			else {
				return isLeftChild() ? (AVLNode<T>)left : (AVLNode<T>)right; 
			}

		}
		
		/*
		 * 更新height
		 * */
		public void updateHeight() {
			int leftHeight = left != null ? ((AVLNode<T>)left).height : 0;
			int rightHeight = right != null ? ((AVLNode<T>)right).height : 0;
			height = Math.max(leftHeight, rightHeight) + 1;
		}
		
		@Override
		public String toString() {
			if (parent == null) {
				return element+"_null";
			}
			return element+"_h:"+height;
		}
		
		
	}
	
	
	
	
	
}
