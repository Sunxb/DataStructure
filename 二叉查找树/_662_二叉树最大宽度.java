package com.sunxb.二叉查找树;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/maximum-width-of-binary-tree/
 * @author sunxiaobin
 *
 */
public class _662_二叉树最大宽度 {
	
	
	
	public int widthOfBinaryTree(TreeNode root) {
    	if (root == null) return 0;
    	
    	Queue<TreeNode> queue = new LinkedList<TreeNode>();
    	Deque<Integer> indexQueue = new LinkedList<Integer>(); //双端
    	
    	queue.offer(root);
    	indexQueue.offer(0);
    	
    	int level = 1;

    	int width = 0;
        	
    	while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			Integer baseIndex = indexQueue.poll();
			
			level --;

			if (node.left != null) {
				queue.offer(node.left);
				indexQueue.offer(baseIndex*2);
			}
			if (node.right != null) {
				queue.offer(node.right);
				indexQueue.offer(baseIndex*2+1);
			}
			
			
			if (level == 0) { // 遍历完一层
				level = queue.size();
				if (level == 0) {
					break;
				}
				
				if (indexQueue.size() == 1) {
					width = Math.max(width, 1);
				}
				else {
					width = Math.max(width, indexQueue.getLast()-indexQueue.getFirst() + 1);
				}
			}
			
		}
    	
    	
    	return width;
    }	
	
//    public int widthOfBinaryTree(TreeNode root) {
//    	if (root == null) return 0;
//    	
//    	Queue<TreeNode> queue = new LinkedList<TreeNode>();
//    	queue.offer(root);
//    	int level = 1;
//
//    		
//    	int start = -1;
//    	int cur = -1;
//    	int end = -1;
//    	int width = 0;
//    	boolean allNull = true;
//    	
//    	List<Integer> list = new ArrayList<Integer>();
//    	
//    	while (!queue.isEmpty()) {
//			TreeNode node = queue.poll();
//			level --;
//			
//			// 处理
//			if (node == null) {
//				if (start < 0) {
//					
//				}
//				else {  // >= 0
//					
//				}
//				
//				cur ++;
//			}
//			else {// node 有值
//				cur ++;
//				
//				if (start < 0) {
//					start = cur;
//				}
//				else{
//					
//				}
//
//				end = cur;
//			}
//			
//			// -------- 
//			if (node != null) {
//				queue.offer(node.left);
//				queue.offer(node.right);
//				if (allNull == true) {
//					allNull = !(node.left!=null || node.right!=null);
//				}
//				
//			}
//			else {
//				queue.offer(null);
//				queue.offer(null);
//			}
//			
//			
//			if (level == 0) { // 遍历完一层
//				level = queue.size();
////				levelNum = level; // 记录下一层的元素个数
//				
//				if (start < 0) { // 当前层全部null
//					break;
//				}
//				if (start == cur) { // 只有一个不为null的结点
//					width = Math.max(width, 1);
//				}
//				else {
//					width = Math.max(width, end-start +1);
//				}
//				System.out.println("====:"+width);	
//				
//				if (allNull == true) {
//					break;
//				}
//				
//				allNull = true;
//				
//				start = -1;
//				end = -1;
//				cur = -1;
//			}
//			
//		}
//    	
//    	
//    	return width;
//    }	
    
    
    public TreeNode createBinaryTreeByArray(Integer []array,int index) {
        TreeNode tn = null;
        if (index<array.length) {
            Integer value = array[index];
            if (value == null) {
                return null;
            }
            tn = new TreeNode(value);
            tn.left = createBinaryTreeByArray(array, 2*index+1);
            tn.right = createBinaryTreeByArray(array, 2*index+2);
            return tn;
        }
        return tn;
    }

    
}
