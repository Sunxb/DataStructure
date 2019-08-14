package com.sunxb.二叉查找树;

import java.util.Comparator;

public class RBTree<T> extends BBST<T> {
	
	private static final boolean BLACK = false;
	private static final boolean RED = true;
	
	public RBTree() {
		this(null);
	}
	public RBTree(Comparator<T> comparetor) {
		super(comparetor);
	}
	
	/*
	 * 添加结点
	 * node是新添加的结点
	 * */
	@Override
	protected void afterAdd(Node<T> node) {
		// 如果node是根结点,直接变成黑色
		if (node.parent == null) {
			black(node);
			return;
		}
		
		// 如果父结点是黑色
		if (isBlack(node.parent)) {
			// 不用任何处理
			return;
		}
		
		Node<T> parent = node.parent;
		Node<T> uncle = parent.sibling();
		Node<T> grand = parent.parent;
		
		// 叔父结点是红色  B树结点上溢
		if (isRed(uncle)) {
			black(parent);
			black(uncle);
			red(grand);
			afterAdd(grand);
			return;
		}
		
		// 叔父结点 不是红色
		if (isBlack(uncle)) {
			
			if (parent.isLeftChild()) { // L
				if (node.isLeftChild()) { // LL
					black(parent);
				}
				else { // LR
					rotateLeft(parent);
					black(node);
				}
				red(grand);
				rotateRight(grand);
			}
			else { // R
				if (node.isLeftChild()) { // RL
					rotateRight(parent);	
					black(node);
				}
				else { // RR
					black(parent);
				}
				red(grand);
				rotateLeft(grand);
			}
		}

	}
	
	@Override
	protected void afterRemove(Node<T> node, Node<T> replace) {
		// 红色结点删除完不需要处理
		if (isRed(node)) {
			return;
		}
		
		// 我们这里的replace其实就是其中一个子结点
		// 如果有node两个红色子结点的话, 那肯定删除的不是node, 所以被删除的node少于两个子结点(1或者0)
		// 拥有一个红色子节点的node
		if (isRed(replace)) {
			
		}
		
	}
	
	
	/*
	 * 设置颜色
	 * */
	private Node<T> color(Node<T> node, boolean color) {
		if (node == null) return null;
		
		((RBNode<T>)node).color = color;
		return node;
	}
	
	/*
	 * 设为红色
	 * */
	private Node<T> red(Node<T> node) {
		return color(node, RED);
	}
	
	/*
	 * 设为黑色
	 * */
	private Node<T> black(Node<T> node) {
		return color(node, BLACK);
	}
	
	/*
	 * 获取颜色
	 * */
	private boolean colorOf(Node<T> node) {
		return node == null ? BLACK : ((RBNode<T>)node).color;
	}
	
	/*
	 * 是否为黑色
	 * */
	private boolean isBlack(Node<T> node) {
		return colorOf(node) == BLACK;
	}
	
	/*
	 * 是否为红色
	 * */
	private boolean isRed(Node<T> node) {
		return colorOf(node) == RED;
	}
	
	@Override
	protected Node<T> createNode(T element, Node<T> parent) {
		return new RBNode<T>(element, parent);
	}
	
	/*
	 * 结点
	 * */
	private static class RBNode<T> extends Node<T>{
		// 结点初始为红色, 这样可以更容易满足 红黑树的5条性质
		boolean color = RED;
		
		public RBNode(T element, Node<T> parent) {
			super(element, parent);
		}
		
		@Override
		public String toString() {
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}
		
	}
	
}
