package com.sunxb.二叉查找树;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
* https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/
跟之前算二叉树高度一样，就是记录好遍历每一层结束的点
*/
public class _107_二叉树的层次遍历2 {
	
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return null;
        
        // 层序遍历 (类似计算高度)
        
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int level = 1;
        
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        List<Integer> subList = new ArrayList<Integer>();
        
        while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			subList.add(node.val);
			
			level --;
			
			if (node.left != null) queue.offer(node.left);
			if (node.right != null) queue.offer(node.right);
			
			if (level == 0) {// 每当level==0 就说明遍历完一层
				list.set(0, subList);
				subList = new ArrayList<Integer>();
				level = queue.size();
			}
		}
        
		return list;
    }
	
	
}
