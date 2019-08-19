package com.sunxb.二叉查找树;

import java.util.Comparator;

/**
 * BBST : Balance Binary Search Tree
 * 因为AVL树和红黑树都继承自BST, 而且都有旋转操作, 所以单独抽取了BBST这一层,把旋转结点的方法放在这一层李
 * 平衡的二叉查找树
 */

public class BBST<T> extends BinarySearchTree<T> {
	public BBST() {
		this(null);
	}
	public BBST(Comparator<T> comparetor) {
		super(comparetor);
	}
	
	/*
	 * 右旋
	 * */
	protected void rotateRight(Node<T> grand) {
		
		Node<T> parent = grand.left;
		Node<T> child = parent.right;
		
		grand.left = child;
		parent.right = grand;

		afterRotate(grand, parent, child);
	}
	
	/*
	 * 左旋
	 * */
	protected void rotateLeft(Node<T> grand) {
		Node<T> parent = grand.right;
		Node<T> child = parent.left;
		
		grand.right = child;
		parent.left = grand;
		
		afterRotate(grand, parent, child);
	}
	
	protected void afterRotate(Node<T> grand, Node<T> parent, Node<T> child) {
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
		
	}
}
