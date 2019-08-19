package com.sunxb.二叉查找树;

import java.util.Comparator;

public class RBTree<T> extends BBST<T> {
	
	private static final boolean BLACK = false;
	private static final boolean RED = true;
	
	public RBTree() {
		this(null);
	}
	public RBTree(Comparator<T> comparator) {
		super(comparator);
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
		// 1. 红色结点删除完不需要处理
		if (isRed(node)) return;
//		
//		// 我们这里的replace其实就是其中一个子结点
//		// 如果有node两个红色子结点的话, 那肯定删除的不是node, 所以被删除的node少于两个子结点(1或者0)
//		// 2. 删除拥有一个红色子节点的node
		if (isRed(replace)) {
			black(replace);
			return;
		}
		
		Node<T> parent = node.parent;
		// 根结点
		if (parent == null) return;
		
		
		// 走到这就是删除 黑叶子
		
		// 该结点在父结点的左还是右
		boolean left = parent.left == null || node.isLeftChild();
		// 兄弟结点
		Node<T> sibling = left ? parent.right : parent.left;
		
		// 被删除的结点在左边 (与下面的在右边其实是操作对称的)
		if (left) {
			if (isRed(sibling)) {
				black(sibling);
				red(parent);
				rotateLeft(parent);
				sibling = parent.right;
			}
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				boolean parentBlack = isBlack(parent); // 提前判断 因为下面有把parent染黑的操作
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent, null);
				}
			}
			else {
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					sibling = parent.right;
				}
				rotateLeft(parent);
				color(sibling, colorOf(parent));
				black(sibling.right);
				black(parent);
			}
					
		}
		
		// 被删除的结点在右边
		else {
			// 红兄弟的情况  经过转化可变为黑兄弟的情况 , 所以先处理红兄弟的情况
			if (isRed(sibling)) {
				black(sibling);
				red(parent);
				rotateRight(parent);
				sibling = parent.left;
			}
			
			// 黑兄弟
			// 兄弟结点的两个子结点都是黑色 (两个结点都是null) 
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				boolean parentBlack = isBlack(parent); // 提前判断 因为下面有把parent染黑的操作
				black(parent);
				red(sibling);
				if (parentBlack) {
					afterRemove(parent, null);
				}
			}
			// 兄弟结点至少有一个红色的子结点
			else {
				// 有三种情况  1仅有一个左  2一仅有个右  3左右都有
				// 没有左的情况是LR 旋转两次 所以先处理这个情况 先左转 然后统一处理右转
				if (isBlack(sibling.left)) {
					//
					rotateLeft(sibling);
					sibling = parent.left;
				}
				
				// 统一处理右转
				rotateRight(parent);
				color(sibling, colorOf(parent));
				black(sibling.left);
				black(parent);
			}
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
