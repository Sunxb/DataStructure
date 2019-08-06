package com.sunxb.二叉查找树;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mj.printer.BinaryTreeInfo;
import com.mj.printer.BinaryTrees;


public class TreeOrderManager<T> implements BinaryTreeInfo{
	
	private Node<T> root;
	
	/**
	 * 
	 * @param preorder  前序遍历
	 * @param inorder   中序遍历
	 * @return    		后序遍历
	 */
	public List<T> outputPostorderFromPreorderAndInorder(List<T> preorder, List<T> inorder) {
		if (preorder == null || inorder== null || preorder.isEmpty() || inorder.isEmpty() || preorder.size() != inorder.size()) {
			return null;
		}
		
		// 得到还原的二叉树的根节点
		Node<T> root = rootNodeFromPreorderAndInorder(preorder, inorder, null);
		this.root = root;
		
		// 后序遍历
		List<T> list = postorderTravesal(root);
		return list;
	}
	
	// 根据前序遍历和中序遍历的结果,还原出二叉树
	private Node<T> rootNodeFromPreorderAndInorder(List<T> preorder, List<T> inorder, Node<T> parent) {
		if (preorder == null || inorder== null || preorder.isEmpty() || inorder.isEmpty()) return null;
		// root节点  就是前序遍历的第一个
		
		T head = preorder.get(0);
		// head 在中序数组中的下标
		int index = inorder.indexOf(head);
		// 中序数组分为两个
		List<T> inorderLeft = inorder.subList(0, index);
		List<T> inorderRight = inorder.subList(index+1, inorder.size());
 		// 根据中序的两个子数组的元素数量   把前序也分为两个
		int leftCount= inorderLeft.size();
		int rightCount= inorderRight.size();
		
		// 前序数组分为两个
		List<T> preorderLeft = preorder.subList(1, 1+leftCount);
		List<T> preorderRight = preorder.subList(preorder.size()-rightCount, preorder.size());
		
		Node<T> root = new Node<T>(head, parent);
		
		Node<T> left = rootNodeFromPreorderAndInorder(preorderLeft, inorderLeft, root);
		Node<T> right = rootNodeFromPreorderAndInorder(preorderRight, inorderRight, root);
		
		if (left != null) {
			root.left = left;
		}
		if (right != null) {
			root.right = right;
		}
		
		return root;
	}
	
	
	
	
	public static void main(String[] args) {
		Integer[] arr1 = new Integer[] { // 前序
//			8,5,11,7,13,9,1,15,21,10
				5,7,11,8,12,6,9,10,3,2,1,15,13
		};
			
		Integer[] arr2 = new Integer[] { // 中序
//			11,5,13,7,8,1,9,21,15,10
				8,11,7,6,12,5,3,10,2,9,1,13,15
		};
		
		List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(arr1));
		List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(arr2));
		
		TreeOrderManager<Integer> manager = new TreeOrderManager<Integer>();
		List<Integer> list = manager.outputPostorderFromPreorderAndInorder(list1, list2);
		
		BinaryTrees.println(manager);		
		System.out.println(list);
	}
	
	/*
	 * 后序遍历
	 * */
	private List<T> postorderTravesal(Node<T> node) {
		List<T> list = new ArrayList<T>();
		postorderTravesal_(node, list);
		return list;
	}
	private void postorderTravesal_(Node<T> node, List<T> list) {
		if (node == null) return;
		
		postorderTravesal_(node.left, list);
		postorderTravesal_(node.right, list);
		list.add(node.element);
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
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			if (parent == null) {
				return element+"_"+"par:null";
			}
			return element+"_"+"par:"+parent.element;
		}
	}
	
	//////////////////////////////
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
