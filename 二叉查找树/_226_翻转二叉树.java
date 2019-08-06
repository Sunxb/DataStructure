package com.sunxb.二叉查找树;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/
 * @author sunxiaobin
 *
 */
public class _226_翻转二叉树 {
	
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return root;
        
        // 使用前序遍历这个二叉树
        // TODO: 处理节点
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
        
    }
    
}
