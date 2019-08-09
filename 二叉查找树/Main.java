package com.sunxb.二叉查找树;

import java.util.List;
import java.util.Random;

import com.mj.printer.BinaryTrees;

public class Main {
	public static void main(String[] args) {
		test2();
	}
	
	private static void test1() {
		TreeNode node1 = new TreeNode(1);
		
		TreeNode node2 = new TreeNode(3);
		
		TreeNode node3 = new TreeNode(2);
		
		TreeNode node4 = new TreeNode(5);
		
		TreeNode node5 = new TreeNode(9);
		
		TreeNode node6 = new TreeNode(6);
		
		TreeNode node7 = new TreeNode(7);

		
		node1.left = node2; 
		node1.right = node3;
		
		node2.left = node5;
		node4.left = node6;
		
		node3.right = node5;
		node5.right = node7;
		
		_662_二叉树最大宽度 test = new _662_二叉树最大宽度();
		
		Integer[] arr = new Integer[]{
			1
		};
        TreeNode root = test.createBinaryTreeByArray(arr,0);
        System.out.println(root);
		
		int i = test.widthOfBinaryTree(root);
		System.out.println(i);
	}
	
	private static void test2() {
		Integer[] arr = new Integer[] {
			12,25,1,52,43,3,32,66,15,18,34,6,9,28,2,14,27,36
//				20,10,30,5,11,25,33,4,6,12,7
		};
		
		BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<Integer>();
		
		Random r = new Random(1);
		for(int i=0 ; i<30 ;  i++) {
			int ran1 = r.nextInt(100);
			binarySearchTree.add(ran1);
		}

		
		BinaryTrees.println(binarySearchTree);
		
		List l = binarySearchTree.inorderTraversal();
		System.out.println(l);
		
		List l2 = binarySearchTree.inorderTraversal2();
		System.out.println(l2);
		
		
		
		
		
	//	System.out.println("高度:"+binarySearchTree.height());
	//	System.out.println("高度:"+binarySearchTree.height2());
	}
}
